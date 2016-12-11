package com.coursescenter.viewconsole;

import com.coursescenter.config.EntityManager.SGDB;
import com.coursescenter.dao.CourseDAO;
import com.coursescenter.dao.InstructorDAO;
import com.coursescenter.dao.ReportDAO;
import com.coursescenter.dao.RequestDAO;
import com.coursescenter.dao.RequestDetailDAO;
import com.coursescenter.dao.StudentDAO;
import com.coursescenter.helper.Aleatory;
import com.coursescenter.pojo.Course;
import com.coursescenter.pojo.Instructor;
import com.coursescenter.pojo.Report;
import com.coursescenter.pojo.Request;
import com.coursescenter.pojo.RequestDetail;
import com.coursescenter.pojo.Student;

import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthLongestWordMinCol;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Program
{
	private static Scanner scanner = new Scanner(System.in);
    private static int option = 0;
    private static SGDB connectedDataBase = SGDB.SQLServer;
    private static SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm");
    private static SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static NumberFormat formatter = NumberFormat.getCurrencyInstance();
    
    public static void main(String[] args) 
    {
    	@SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.OFF);
    	
        do {
        	menu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.println("\n");
                    reader();
                    break;
                case 2:
                    System.out.println("\n");
                    requestGenerate();
                    break;
                case 3:
                    System.out.println("\n");
                    selectAllInstructors();
                    break;
                case 4:
                	System.out.println("\n");
                	selectAllCourses();
                	break;
                case 5:
                	System.out.println("\n");
                	selectAllStudents();
                	break;
                case 6:
                	System.out.println("\n");
                	selectAllRequests();
                	break;
                case 7:
                	System.out.println("\n");
                	selectAllRequestDetails();
                	break;
                case 8:
                	System.out.println("\n");
                	selectTotalCourseMinisteredByInstructor();
                	break;
                case 9:
                	System.out.println("\n");
                	selectTotalRequestCoursePriceByCourseType();
                	break;
                case 10:
                	System.out.println("\n");
                	selectTotalRequestCoursePriceByCourse();
                	break;
                case 11:
                	System.out.println("\n");
                	selectTotalRequestCoursePriceByStudent();
                	break;
                case 12:
                	System.out.println("\n");
                	selectCourseBestSeller();
                	break;
                case 13:
                	System.out.println("\n");
                	selectStudentsRegisterInCourses();
                	break;
                case 14:
                	System.out.println("\n");
                	selectStudentsWithoutRequests();
                	break;
                case 15:
                	System.out.println("\n");
                	alterProviderStudentsEmail();
                	break;
                case 16:
                	System.out.println("\n");
                	deleteRequestsWithoutCourses();
                	break;
                case 99:
                	System.out.println("\n");
                	alterConnectedSGDB();
                	break;
                default:
                    System.out.println("\n");
                    System.err.println(option + " não é uma opção válida.");
                    System.out.println("\n");
                break;
            }
        } while (option != 0);
        scanner.close();
    }

    private static void menu()
    {
    	System.out.println("┌───────────────────┤ MENU ├─────────────────┐");
		System.out.println("│ Inserts                                    │");
		System.out.println("│ 1. Importar Cadastro de Alunos (*.csv)     │");
		System.out.println("│ 2. Gerar Pedidos Automáticos               │");
		System.out.println("│                                            │");
		System.out.println("│ Selects                                    │");
		System.out.println("│ 3. Instrutores                             │");
		System.out.println("│ 4. Cursos                                  │");
		System.out.println("│ 5. Alunos                                  │");
		System.out.println("│ 6. Pedidos                                 │");
		System.out.println("│ 7. Detalhes dos Pedidos                    │");
		System.out.println("│ 8. Total de Cursos por Instrutor           │");
		System.out.println("│ 9. Total dos Pedidos por Tipo de Curso     │");
		System.out.println("│ 10. Total dos Pedidos por Curso            │");
		System.out.println("│ 11. Total dos Pedidos por Aluno            │");
		System.out.println("│ 12. Curso mais Pedido                      │");
		System.out.println("│ 13. Alunos Matrículados nos Cursos         │");
		System.out.println("│ 14. Alunos que Não Possuem Pedidos         │");
		System.out.println("│                                            │");
		System.out.println("│ Update                                     │");
		System.out.println("│ 15. Alterar E-mail dos Alunos              │");
		System.out.println("│                                            │");
		System.out.println("│ Delete                                     │");
		System.out.println("│ 16. Excluir Pedidos sem Cursos             │");
		System.out.println("│                                            │");
		System.out.println("│ Database                                   │");
		System.out.println("│ 99. Alterar SGDB                           │");
		System.out.println("│                                            │");
		System.out.println("│ 0. Sair                                    │");
		System.out.println("├────────────────────────────────────────────┤");
		System.out.println("│ Escolha uma opção e pressione Enter        │");
		System.out.println("└────────────────────────────────────────────┘");
		System.out.print("Opção: ");
    }
    private static void alterConnectedSGDB()
    {
        if (connectedDataBase == SGDB.SQLServer)
        {
            connectedDataBase = SGDB.MySQL;
        }
        else if (connectedDataBase == SGDB.MySQL)
        {
            connectedDataBase = SGDB.SQLServer;
        }

        System.out.println("O SGBD foi alterado para: " + connectedDataBase.toString() + ".");
        System.out.println("\n");
    }
    
    private static void reader()
    {
    	try
    	{
    		System.out.println("Carregando tela de seleção de arquivo.");
    	
    		JFileChooser chooser = new JFileChooser(System.getProperty("java.class.path"));
    		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
    		chooser.setFileFilter(filter);
    		int result = chooser.showOpenDialog(null);
    	
    		if (result == JFileChooser.APPROVE_OPTION)
    		{
    			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(chooser.getSelectedFile().getAbsolutePath())));
    			String line =  "";

    			if (bufferedReader.ready())
    			{
    				int rowCount = 0;
    				int newStudents = 0;
                    int repeatedStudents = 0;
    				
                    StudentDAO studentDAO = new StudentDAO(connectedDataBase);
                    List<Student> students = new ArrayList<Student>();
    				while ((line = bufferedReader.readLine()) != null)
        			{
    					String[] partsStudent = line.split(";");
    					
    					Student student = new Student();
    					student.setIdentification(partsStudent[0].toString());
    					student.setBirthDate(inputDateFormat.parse(partsStudent[1]));
    					student.setEmail(partsStudent[2].toString());
    					student.setRegistration(partsStudent[3].toString());
    					
    					students.add(student);
						Student verifyStudent = studentDAO.getByIdentification(student.getIdentification());
    					if (verifyStudent == null)
    					{
    						students.add(student);
    						newStudents++;
    					}
						else
    					{
    						repeatedStudents++;
    					}
    					
    					rowCount++;
        	        }
    				
    				long timeInitial = System.currentTimeMillis();
    				if (!students.isEmpty())
    				{
        				studentDAO = new StudentDAO(connectedDataBase);
        				studentDAO.save(students);
    				}
    				long timeFinal = System.currentTimeMillis();
    				
    				System.out.println("O arquivo possui " + rowCount + " registros.");
    				if (newStudents > 0)
    				{
    					System.out.println("Foram inseridos " + newStudents + " novos registros.");
    				}
    				if (repeatedStudents > 0) 
					{
    					System.out.println(repeatedStudents + " registros foram ignorados pois já existem na base de dados.");
					}
    				System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
    				System.out.println("SGBD: " + connectedDataBase.toString() + ".");
    				System.out.println("\n");
    				bufferedReader.close();
    			}
    			else
    			{
    				System.out.println("O arquivo selecionado não possui registros.");
        			System.out.println("\n");
    			}
    		}
    		else
    		{
    			System.err.println("Processo de importação de arquivo cancelado.");
    			System.out.println("\n");
    		}
    	}
		catch (FileNotFoundException ex)
		{
			System.err.println("Erro do sistema: " + ex.getMessage() + ".");
       	 	System.out.println("\n");
		}
		catch (IOException ex)
		{
			System.err.println("Erro do sistema: " + ex.getMessage() + ".");
       	 	System.out.println("\n");
		} catch (ParseException ex)
    	{
			System.err.println("Erro do sistema: " + ex.getMessage() + ".");
       	 	System.out.println("\n");
		}
    }
    private static void requestGenerate()
    {
    	try
        {
    		Aleatory aleatory = new Aleatory();
    		RequestDetailDAO requestDetailDAO = new RequestDetailDAO(connectedDataBase);
			RequestDAO requestDAO = new RequestDAO(connectedDataBase);
			StudentDAO studentDAO = new StudentDAO(connectedDataBase);
			CourseDAO courseDAO = new CourseDAO(connectedDataBase);
    		
    		System.out.print("Informe a quantidade de pedidos: ");
    		
    		int numberRequests = scanner.nextInt();
    		if (numberRequests > 0)
    		{
    			System.out.println("\nAguarde enquanto o sistema gera os pedidos.");
    			
    			long timeInitial = System.currentTimeMillis();
    			for (int i = numberRequests; i > 0; i--)
    			{
    				Request request = new Request();
    				request.setRequestDateTime(Date.valueOf(aleatory.getDateTime(2015, 2016)));
    				request.setStudent(studentDAO.getRandom());
    				requestDAO.save(request);
    				
    				int sort = aleatory.getInteger(1, 4);
    				while (sort > 0)
    				{
    					Request lastRequest = new Request();
    					lastRequest.setId(requestDAO.getLastId());
    					
    					Course course = new Course();
    					course.setId(courseDAO.getRandom().getId());
    					
    					RequestDetail requestDetail = new RequestDetail();
    					requestDetail.setRequest(lastRequest);
    					requestDetail.setCourse(course);
    					
    					requestDetailDAO.save(requestDetail);
    					
    					sort--;
    				}
    			}
    			long timeFinal = System.currentTimeMillis();
    			
    			System.out.println("Pedidos gerados com sucesso.");
    			System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
    			System.out.println("SGBD: " + connectedDataBase.toString() + ".");
    			System.out.println("\n");
    		}
    		else
    		{
    			System.err.println(numberRequests + " não foi reconhecido como um número válido.");
           	 	System.out.println("\n");
    		}    		
        }
        catch (Exception ex)
        {
       	 	System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
        }
    }
    
    private static void selectAllInstructors()
    {
    	 try
         {
    		 InstructorDAO instructorDAO = new InstructorDAO(connectedDataBase);
    		 
    		 long timeInitial = System.currentTimeMillis();
    		 List<Instructor> instructors = instructorDAO.getList();
    		 long timeFinal = System.currentTimeMillis();
    		 
    		 if (!instructors.isEmpty())
    		 {
    			 System.out.println("Listagem dos instrutores cadastrados no sistema\n");
    			 V2_AsciiTable at = new V2_AsciiTable();
    			 at.addRule();
    			 at.addRow("Id", "Identificação");
    			 at.addRule();
    			 for (Instructor instructor : instructors)
    			 {
    				 at.addRow(instructor.getId(), instructor.getIdentification());
    				 at.addRule();
    			 }
    			 V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
    			 rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
    			 rend.setWidth(new WidthLongestWordMinCol(new int[] { 3, 25 }));
    			 
    			 RenderedTable rt = rend.render(at);
    			 System.out.println(rt);
    			 System.out.println(instructors.size() + " registros encontrados.");
    			 System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
    			 System.out.println("SGBD: " + connectedDataBase.toString() + ".");
    		 }
    		 else
    		 {
    			 System.out.println("Nenhum resultado encontrado.");
    			 System.out.println("\n");
    		 }
         }
         catch (Exception ex)
         {
        	 System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
        	 System.out.println("\n");
         }
    }
    private static void selectAllCourses()
    {
    	try
    	{
    		CourseDAO courseDAO = new CourseDAO(connectedDataBase);
    		
    		long timeInitial = System.currentTimeMillis();
    		List<Course> courses = courseDAO.getList();
    		long timeFinal = System.currentTimeMillis();
    		
    		if (!courses.isEmpty())
    		{
    			System.out.println("Listagem dos cursos cadastrados no sistema\n");
    			V2_AsciiTable at = new V2_AsciiTable();
    			at.addRule();
    			at.addRow("Id", "Identificação", "Preço", "Tipo do Curso", "Instrutor");
    			at.addRule();
   			 	for (Course course : courses)
   			 	{
   			 		at.addRow(course.getId(),
		 				course.getIdentification(),
		 				formatter.format(course.getPrice()),
		 				course.getCourseType().getIdentification(),
		 				course.getInstructor().getIdentification()
	 				);
   			 		at.addRule();
   			 	}
   			 	V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
   			 	rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
   				rend.setWidth(new WidthLongestWordMinCol(new int[] { 3, 40, 15, 20, 25 }));

   				RenderedTable rt = rend.render(at);
   				System.out.println(rt);
   			 	System.out.println(courses.size() + " registros encontrados.");
   			 	System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
   			 	System.out.println("SGBD: " + connectedDataBase.toString() + ".");
   			 	System.out.println("\n");
    		}
    		else
    		{
    			System.out.println("Nenhum resultado encontrado.");
   			 	System.out.println("\n");
    		}
    	}
    	catch (Exception ex)
    	{
    		System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
    	}
    }
    private static void selectAllStudents()
    {
    	try
    	{
    		StudentDAO studentDAO = new StudentDAO(connectedDataBase);
    		
    		long timeInitial = System.currentTimeMillis();
    		List<Student> students = studentDAO.getList();
    		long timeFinal = System.currentTimeMillis();
    		
    		if (!students.isEmpty())
    		{
    			System.out.println("Listagem dos alunos cadastrados no sistema\n");
    			V2_AsciiTable at = new V2_AsciiTable();
    			at.addRule();
    			at.addRow("R.A.", "Identificação", "Data de Aniversário", "E-mail");
    			at.addRule();
    			for (Student student : students)
    			{	
    				at.addRow(student.getRegistration(),
    					student.getIdentification(),
    					outputDateFormat.format(student.getBirthDate()),
    					student.getEmail()
    				);
    				at.addRule();
    			}
    			V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
    			rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
    			rend.setWidth(new WidthLongestWordMinCol(new int[] { 10, 35, 18, 40 }));
    			
    			RenderedTable rt = rend.render(at);
    			System.out.println(rt);
    			System.out.println(students.size() + " registros encontrados.");
    			System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
    			System.out.println("SGBD: " + connectedDataBase.toString() + ".");
    			System.out.println("\n");
    		}
    		else
    		{
    			System.out.println("Nenhum resultado encontrado.");
   			 	System.out.println("\n");
    		}
    	}
    	catch (Exception ex)
    	{
    		System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
    	}
    }    
    private static void selectAllRequests()
    {
    	try
    	{
    		RequestDAO requestDAO = new RequestDAO(connectedDataBase);
    		
    		long timeInitial = System.currentTimeMillis();
    		List<Request> requests = requestDAO.getList();
    		long timeFinal = System.currentTimeMillis();
    		
    		if (!requests.isEmpty())
    		{
    			int totalCourseRequest = 0;
    			double totalCoursePrice = 0;
    			
    			System.out.println("Listagem dos pedidos cadastrados no sistema\n");
    			V2_AsciiTable at = new V2_AsciiTable();
    			at.addRule();
    			at.addRow("Id", "Data do Pedido", "Aluno", "Qtd. Cursos", "Total do Pedido");
    			at.addRule();
   			 	for (Request request : requests)
   			 	{
   			 		totalCourseRequest += request.getRequestDetails().size();
   			 		double totalRequestCoursePrice = 0;
   			 		
   			 		for (RequestDetail detail : request.getRequestDetails())
   			 		{
   			 			totalRequestCoursePrice += detail.getCourse().getPrice();
   			 			totalCoursePrice += detail.getCourse().getPrice();
   			 		}
   			 		
   			 		at.addRow(request.getId(),
		 				outputDateFormat.format(request.getRequestDateTime()),
		 				request.getStudent().getIdentification(),
		 				request.getRequestDetails().size(),
		 				formatter.format(totalRequestCoursePrice)
	 				);
		 			at.addRule();
   			 	}
   			 	V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
   			 	rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
   				rend.setWidth(new WidthLongestWordMinCol(new int[] { 5, 18, 35, 10, 20 }));

   				RenderedTable rt = rend.render(at);
   				System.out.println(rt);
   			 	System.out.println(requests.size() + " registros encontrados.");
   			 	System.out.println("Quantidade total de cursos pedidos: " + totalCourseRequest);
			 	System.out.println("Valor total dos cursos pedidos: " + formatter.format(totalCoursePrice));
			 	System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
			 	System.out.println("SGBD: " + connectedDataBase.toString() + ".");
   			 	System.out.println("\n");
    		}
    		else
    		{
    			System.out.println("Nenhum resultado encontrado.");
   			 	System.out.println("\n");
    		}
    	}
    	catch (Exception ex)
    	{
    		System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
    	}
    }
    private static void selectAllRequestDetails()
    {
    	try
    	{
    		RequestDetailDAO requestDetailDAO = new RequestDetailDAO(connectedDataBase);
    		long timeInitial = System.currentTimeMillis();
    		List<RequestDetail> requestDetails = requestDetailDAO.getList();
    		long timeFinal = System.currentTimeMillis();
    		
    		if (!requestDetails.isEmpty())
    		{
    			int totalCourseRequest = requestDetails.size();
    			double totalCoursePrice = 0;
    			
    			System.out.println("Listagem dos detalhes dos pedidos cadastrados no sistema\n");
    			V2_AsciiTable at = new V2_AsciiTable();
    			at.addRule();
    			at.addRow("Id", "Aluno", "Data do Pedido", "Tipo do Curso", "Curso", "Preço");
    			at.addRule();
   			 	for (RequestDetail detail : requestDetails)
   			 	{
   			 		at.addRow(detail.getId(),
		 				detail.getRequest().getStudent().getIdentification(),
		 				outputDateFormat.format(detail.getRequest().getRequestDateTime()),
		 				detail.getCourse().getCourseType().getIdentification(),
		 				detail.getCourse().getIdentification(),
		 				formatter.format(detail.getCourse().getPrice())
	 				);
   			 		at.addRule();
   			 		
   			 		totalCoursePrice += detail.getCourse().getPrice();
   			 	}
   			 	V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
   			 	rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
   				rend.setWidth(new WidthLongestWordMinCol(new int[] { 5, 35, 18, 20, 40, 15 }));

   				RenderedTable rt = rend.render(at);
   				System.out.println(rt);
   			 	System.out.println(requestDetails.size() + " registros encontrados.");
   			 	System.out.println("Quantidade total de cursos pedidos: " + totalCourseRequest);
   			 	System.out.println("Valor total dos cursos pedidos: " + formatter.format(totalCoursePrice));
   			 	System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
   			 	System.out.println("SGBD: " + connectedDataBase.toString() + ".");
   			 	System.out.println("\n");
    		}
    		else
    		{
    			System.out.println("Nenhum resultado encontrado.");
   			 	System.out.println("\n");
    		}
    	}
    	catch (Exception ex)
    	{
    		System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
    	}
    }
    
    private static void selectTotalCourseMinisteredByInstructor()
    {
    	try
    	{
    		ReportDAO reportDAO = new ReportDAO(connectedDataBase);
    		
    		long timeInitial = System.currentTimeMillis();
    		List<Report> results = reportDAO.getListCountGroupByInstructor();
    		long timeFinal = System.currentTimeMillis();
    		
    		if (!results.isEmpty())
    		{
    			int totalCourseMinister = 0;
    			
    			System.out.println("Relatório de total de cursos ministrados por instrutor\n");
    			V2_AsciiTable at = new V2_AsciiTable();
    			at.addRule();
    			at.addRow("Instrutor", "Qtd. Cursos");
    			at.addRule();
   			 	for (Report result : results)
   			 	{
   			 		at.addRow(result.getInstructorIdentification(), result.getTotalCourseMinistered());
   			 		at.addRule();
   			 		
   			 		totalCourseMinister += result.getTotalCourseMinistered();
   			 	}
   			 	V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
   			 	rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
   				rend.setWidth(new WidthLongestWordMinCol(new int[] { 25, 10 }));

   				RenderedTable rt = rend.render(at);
   				System.out.println(rt);
   			 	System.out.println(results.size() + " registros encontrados.");
   			 	System.out.println("Quantidade total de cursos ministrados: " + totalCourseMinister);
   			 	System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
   			 	System.out.println("SGBD: " + connectedDataBase.toString() + ".");
   			 	System.out.println("\n");
    		}
    		else
    		{
    			System.out.println("Nenhum resultado encontrado.");
   			 	System.out.println("\n");
    		}
    	}
    	catch (Exception ex)
    	{
    		System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
    	}
    }
    private static void selectTotalRequestCoursePriceByCourseType()
    {
    	try
    	{
    		ReportDAO reportDAO = new ReportDAO(connectedDataBase);
    		
    		long timeInitial = System.currentTimeMillis();
    		List<Report> results = reportDAO.getListSumGroupByCourseType();
    		long timeFinal = System.currentTimeMillis();
    		
    		if (!results.isEmpty())
    		{
    			int totalCourseRequest = 0;
                double totalCoursePrice = 0;

    			System.out.println("Relatório de total de cursos pedidos por tipo de curso\n");
    			V2_AsciiTable at = new V2_AsciiTable();
    			at.addRule();
    			at.addRow("Tipo do Curso", "Qtd. Cursos", "Preço Total");
    			at.addRule();
   			 	for (Report result : results)
   			 	{
   			 		at.addRow(result.getCourseTypeIdentification(),
		 				result.getTotalCourseRequest(),
		 				formatter.format(result.getTotalCoursePrice())
					);
   			 		at.addRule();
   			 		
   			 		totalCourseRequest += result.getTotalCourseRequest();
   			 		totalCoursePrice += result.getTotalCoursePrice();
   			 	}
   			 	V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
   			 	rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
				rend.setWidth(new WidthLongestWordMinCol(new int[] { 20, 10, 15 }));

   				RenderedTable rt = rend.render(at);
				System.out.println(rt);
   			 	System.out.println(results.size() + " registros encontrados.");
   			 	System.out.println("Quantidade total de cursos pedidos: " + totalCourseRequest);
			 	System.out.println("Valor total dos cursos pedidos: " + formatter.format(totalCoursePrice));
			 	System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
			 	System.out.println("SGBD: " + connectedDataBase.toString() + ".");
   			 	System.out.println("\n");
    		}
    		else
    		{
    			System.out.println("Nenhum resultado encontrado.");
   			 	System.out.println("\n");
    		}
    	}
    	catch (Exception ex)
    	{
    		System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
    	}
    }
    private static void selectTotalRequestCoursePriceByCourse()
    {
    	try
    	{
    		ReportDAO reportDAO = new ReportDAO(connectedDataBase);
    		
    		long timeInitial = System.currentTimeMillis();
    		List<Report> results = reportDAO.getListSumGroupByCourse();
    		long timeFinal = System.currentTimeMillis();
    		
    		if (!results.isEmpty())
    		{
    			int totalCourseRequest = 0;
                double totalCoursePrice = 0;

    			System.out.println("Relatório de total de cursos pedidos por curso\n");
    			V2_AsciiTable at = new V2_AsciiTable();
    			at.addRule();
    			at.addRow("Curso", "Qtd. Cursos", "Preço Total");
    			at.addRule();
   			 	for (Report result : results)
   			 	{
   			 		at.addRow(result.getCourseIdentification(),
		 				result.getTotalCourseRequest(),
		 				formatter.format(result.getTotalCoursePrice())
	 				);
   			 		at.addRule();
   			 		
   			 		totalCourseRequest += result.getTotalCourseRequest();
   			 		totalCoursePrice += result.getTotalCoursePrice();
   			 	}
   			 	V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
   			 	rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
   				rend.setWidth(new WidthLongestWordMinCol(new int[] { 40, 10, 15 }));

   				RenderedTable rt = rend.render(at);
   				System.out.println(rt);
   			 	System.out.println(results.size() + " registros encontrados.");
   			 	System.out.println("Quantidade total de cursos pedidos: " + totalCourseRequest);
			 	System.out.println("Valor total dos cursos pedidos: " + formatter.format(totalCoursePrice));
			 	System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
			 	System.out.println("SGBD: " + connectedDataBase.toString() + ".");
   			 	System.out.println("\n");
    		}
    		else
    		{
    			System.out.println("Nenhum resultado encontrado.");
   			 	System.out.println("\n");
    		}
    	}
    	catch (Exception ex)
    	{
    		System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
    	}
    }
    private static void selectTotalRequestCoursePriceByStudent()
    {
    	try
    	{
    		ReportDAO reportDAO = new ReportDAO(connectedDataBase);
    		long timeInitial = System.currentTimeMillis();
    		List<Report> results = reportDAO.getListSumGroupByStudent();
    		long timeFinal = System.currentTimeMillis();
    		
    		if (!results.isEmpty())
    		{
    			int totalCourseRequest = 0;
                double totalCoursePrice = 0;

    			System.out.println("Relatório de total de cursos pedidos por aluno\n");
    			V2_AsciiTable at = new V2_AsciiTable();
    			at.addRule();
    			at.addRow("Aluno", "Qtd. Cursos", "Preço Total");
    			at.addRule();
   			 	for (Report result : results)
   			 	{
   			 		at.addRow(result.getStudentIdentification(),
		 				result.getTotalCourseRequest(),
		 				formatter.format(result.getTotalCoursePrice())
	 				);
   			 		at.addRule();
   			 		
   			 		totalCourseRequest += result.getTotalCourseRequest();
   			 		totalCoursePrice += result.getTotalCoursePrice();
   			 	}
   			 	V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
   			 	rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
				rend.setWidth(new WidthLongestWordMinCol(new int[] { 35, 10, 15 }));

   				RenderedTable rt = rend.render(at);
   				System.out.println(rt);
   			 	System.out.println(results.size() + " registros encontrados.");
   			 	System.out.println("Quantidade total de cursos pedidos: " + totalCourseRequest);
			 	System.out.println("Valor total dos cursos pedidos: " + formatter.format(totalCoursePrice));
			 	System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
			 	System.out.println("SGBD: " + connectedDataBase.toString() + ".");
			 	System.out.println("\n");
    		}
    		else
    		{
    			System.out.println("Nenhum resultado encontrado.");
   			 	System.out.println("\n");
    		}
    	}
    	catch (Exception ex)
    	{
    		System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
    	}
    }
    
    private static void selectCourseBestSeller()
    {
    	try
    	{
    		ReportDAO reportDAO = new ReportDAO(connectedDataBase);
    		
    		long timeInitial = System.currentTimeMillis();
    		Report result = reportDAO.getCourseBestSeller();
    		long timeFinal = System.currentTimeMillis();
    		
    		if (result != null)
    		{
    			System.out.println(result.getCourseIdentification() + " é o curso com maior número de pedidos.");
    			System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
    			System.out.println("SGBD: " + connectedDataBase.toString() + ".");
    			System.out.println("\n");
    		}
    		else
    		{
    			System.out.println("Nenhum resultado encontrado.");
   			 	System.out.println("\n");
    		}
    	}
    	catch (Exception ex)
    	{
    		System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
    	}
    }
    private static void selectStudentsRegisterInCourses()
    {
    	try
    	{
    		ReportDAO reportDAO = new ReportDAO(connectedDataBase);
    		
    		long timeInitial = System.currentTimeMillis();
    		List<Report> results = reportDAO.getListStudentsRegisterInCourses();
    		long timeFinal = System.currentTimeMillis();
    		
    		if (!results.isEmpty())
    		{
    			System.out.println("Relatório de total de cursos pedidos por curso\n");
    			V2_AsciiTable at = new V2_AsciiTable();
    			at.addRule();
    			at.addRow("R.A", "Aluno", "Curso", "Tipo do Curso", "Data da Matrícula");
    			at.addRule();
   			 	for (Report result : results)
   			 	{
   			 		at.addRow(result.getStudentRegistration(),
		 				result.getStudentIdentification(),
		 				result.getCourseIdentification(),
		 				result.getCourseTypeIdentification(),
		 				outputDateFormat.format(result.getRequestDateTime())
	 				);
   			 		at.addRule();
   			 	}
   			 	V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
   			 	rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
   				rend.setWidth(new WidthLongestWordMinCol(new int[] { 10, 35, 40, 20, 18 }));

   				RenderedTable rt = rend.render(at);
   				System.out.println(rt);
   			 	System.out.println(results.size() + " registros encontrados.");
   			 	System.out.println("Quantidade total de cursos por alunos: " + results.size());
   			 	System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
   			 	System.out.println("SGBD: " + connectedDataBase.toString() + ".");
   			 	System.out.println("\n");
    		}
    		else
    		{
    			System.out.println("Nenhum resultado encontrado.");
   			 	System.out.println("\n");
    		}
    	}
    	catch (Exception ex)
    	{
    		System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
    	}
    }
    private static void selectStudentsWithoutRequests()
    {
    	try
    	{
    		ReportDAO reportDAO = new ReportDAO(connectedDataBase);
    		
    		long timeInitial = System.currentTimeMillis();
    		List<Report> results = reportDAO.getListStudentsWithoutRequests();
    		long timeFinal = System.currentTimeMillis();
    		
    		if (!results.isEmpty())
    		{
    			System.out.println("Relatório de alunos que não possuem pedidos\n");
    			V2_AsciiTable at = new V2_AsciiTable();
    			at.addRule();
    			at.addRow("R.A.", "Identificação");
    			at.addRule();
   			 	for (Report result : results)
   			 	{
   			 		at.addRow(result.getStudentRegistration(), result.getStudentIdentification());
   			 		at.addRule();
   			 	}
   			 	V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
   			 	rend.setTheme(V2_E_TableThemes.UTF_LIGHT.get());
   				rend.setWidth(new WidthLongestWordMinCol(new int[] { 10, 35 }));

   				RenderedTable rt = rend.render(at);
   				System.out.println(rt);
   			 	System.out.println(results.size() + " registros encontrados.");
   			 	System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
   			 	System.out.println("SGBD: " + connectedDataBase.toString() + ".");
   			 	System.out.println("\n");
    		}
    		else
    		{
    			System.out.println("Nenhum resultado encontrado.");
   			 	System.out.println("\n");
    		}
    	}
    	catch (Exception ex)
    	{
    		System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
    	}
    }
    
    private static void alterProviderStudentsEmail()
    {
    	try
    	{
    		StudentDAO studentDAO = new StudentDAO(connectedDataBase);

    		List<Student> students = studentDAO.getList();
    		if (!students.isEmpty())
    		{
    			System.out.println("Aguarde enquanto o sistema atualiza os e-mails dos alunos.\n");
   			 	for (Student student : students)
   			 	{	
   			 		if (student.getEmail().contains("@provider.com"))
   			 			student.setEmail(student.getIdentification().replace(" ", "").toLowerCase() + "@provedor.com.br");
   			 		else
   			 			student.setEmail(student.getIdentification().replace(" ", "").toLowerCase() + "@provider.com");
   			 	}
   			 	
   			 	long timeInitial = System.currentTimeMillis();
   			 	studentDAO.save(students);
   			 	long timeFinal = System.currentTimeMillis();
   			 	
   			 	System.out.println("\n" + students.size() + " registros alterados com sucesso.");
   			 	System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
   			 	System.out.println("SGBD: " + connectedDataBase.toString() + ".");
   			 	System.out.println("\n");
    		}
    		else
    		{
    			System.out.println("Nenhum resultado encontrado.");
   			 	System.out.println("\n");
    		}
    	}
    	catch (Exception ex)
    	{
    		System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
    	}
    }
    
    private static void deleteRequestsWithoutCourses()
    {
    	try
    	{
    		RequestDAO requestDAO = new RequestDAO(connectedDataBase);
    		List<Request> requests = requestDAO.getRequestsWithoutCourses();
    		
    		if (!requests.isEmpty())
    		{
    			System.out.println("Aguarde enquanto o sistema excluí os pedidos.\n");
    			
    			long timeInitial = System.currentTimeMillis();
    			requestDAO.delete(requests);
    			long timeFinal = System.currentTimeMillis();
    			
   			 	System.out.println(requests.size() + " pedidos foram excluídos com sucesso do sistema.");
   			 	System.out.println("\nDesempenho da operação: " + ConvertMilliseconds((timeFinal - timeInitial)) + ".");
   			 	System.out.println("SGBD: " + connectedDataBase.toString() + ".");
   			 	System.out.println("\n");
    		}
    		else
    		{
    			System.out.println("Nenhum resultado encontrado.");
   			 	System.out.println("\n");
    		}
    	}
    	catch (Exception ex)
    	{
    		System.err.println("Erro do sistema: " + ex.getMessage().toString() + ".");
       	 	System.out.println("\n");
    	}
    }
    
    private static String ConvertMilliseconds(long milliseconds)
    {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milliseconds),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
        
        return hms;
    }
}