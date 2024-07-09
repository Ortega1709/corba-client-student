package client;

import StudentApp.Student;
import StudentApp.StudentManagement;
import StudentApp.StudentManagementHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import java.util.Arrays;
import java.util.List;

public class CorbaClient {

    public static void main(String[] args) {
        try {

            ORB orb = ORB.init(args, null);
            Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            StudentManagement op = StudentManagementHelper.narrow(ncRef.resolve_str("StudentManagement"));

            // get all students
            List<Student> students = Arrays.asList(op.students());
            System.out.println("All students");
            students.forEach(student -> System.out.println(student.name));


            // add students
            op.add("Matricule 1", "Promotion 1", "17/09/2002", "Name 1");
            op.add("Matricule 2", "Promotion 2", "09/09/2001", "Name 2");
            op.add("Matricule 3", "Promotion 2", "10/09/2005", "Name 3");
            op.add("Matricule 4", "Promotion 1", "08/09/2001", "Name 4");

            // get all students
            students = Arrays.asList(op.students());
            System.out.println("All students");
            students.forEach(student -> System.out.println(student.name));


            // get students by promotion
            students = Arrays.asList(op.getStudentsByPromotion("Promotion 1"));
            System.out.println("Student of promotion");
            students.forEach(student -> System.out.println(student.name));


            // modify student date
            op.changeDate("Matricule 1", "05/05/2002");
            students = Arrays.asList(op.students());
            System.out.println("All students");
            students.forEach(student -> System.out.println(student.name));


            // modify promotion
            op.changePromotion("Matricule 2", "Promotion 1");
            students = Arrays.asList(op.students());
            System.out.println("All students");
            students.forEach(student -> System.out.println(student.name));

        } catch (Exception e) {
            System.out.println("Erreur lors de l'ouverture du serveur " + e);
        }
    }

}
