package de.heinerkuecker.coroutine.iterator.test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.heinerkuecker.coroutine.Coroutine;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.condition.Not;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.GetResumeArgument;
import de.heinerkuecker.coroutine.expression.InstanceOf;
import de.heinerkuecker.coroutine.expression.NoVariablesNoArgumentsExpression;
import de.heinerkuecker.coroutine.expression.NullValue;
import de.heinerkuecker.coroutine.step.complex.While;
import de.heinerkuecker.coroutine.step.ret.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.AbstrLocalVarUseWithExpressionStmt;
import de.heinerkuecker.coroutine.step.simple.AddToCollectionLocalVar;
import de.heinerkuecker.coroutine.step.simple.DeclareVariable;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;

/**
 * JUnit4 test case for {@link Coroutine}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineSaxParserTest
{
    // https://www.tutorialspoint.com/java_xml/java_sax_parse_document.htm

    private static final String XML_STR =
            "<?xml version = \"1.0\"?>\r\n" +
                    "<class>\r\n" +
                    "   <student rollno = \"393\">\r\n" +
                    "      <firstname>dinkar</firstname>\r\n" +
                    "      <lastname>kad</lastname>\r\n" +
                    "      <nickname>dinkar</nickname>\r\n" +
                    "      <marks>85</marks>\r\n" +
                    "   </student>\r\n" +
                    "\r\n" +
                    "   <student rollno = \"493\">\r\n" +
                    "      <firstname>Vaneet</firstname>\r\n" +
                    "      <lastname>Gupta</lastname>\r\n" +
                    "      <nickname>vinni</nickname>\r\n" +
                    "      <marks>95</marks>\r\n" +
                    "   </student>\r\n" +
                    "\r\n" +
                    "   <student rollno = \"593\">\r\n" +
                    "      <firstname>jasvir</firstname>\r\n" +
                    "      <lastname>singn</lastname>\r\n" +
                    "      <nickname>jazz</nickname>\r\n" +
                    "      <marks>90</marks>\r\n" +
                    "   </student>\r\n" +
                    "</class>\\r\\n";

    static class Student
    {
        String rollNo;
        String firstName;
        String lastName;
        String nickName;
        String marks;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
            result = prime * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
            result = prime * result + ((this.marks == null) ? 0 : this.marks.hashCode());
            result = prime * result + ((this.nickName == null) ? 0 : this.nickName.hashCode());
            result = prime * result + ((this.rollNo == null) ? 0 : this.rollNo.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Student other = (Student) obj;
            if (this.firstName == null) {
                if (other.firstName != null) {
                    return false;
                }
            } else if (!this.firstName.equals(other.firstName)) {
                return false;
            }
            if (this.lastName == null) {
                if (other.lastName != null) {
                    return false;
                }
            } else if (!this.lastName.equals(other.lastName)) {
                return false;
            }
            if (this.marks == null) {
                if (other.marks != null) {
                    return false;
                }
            } else if (!this.marks.equals(other.marks)) {
                return false;
            }
            if (this.nickName == null) {
                if (other.nickName != null) {
                    return false;
                }
            } else if (!this.nickName.equals(other.nickName)) {
                return false;
            }
            if (this.rollNo == null) {
                if (other.rollNo != null) {
                    return false;
                }
            } else if (!this.rollNo.equals(other.rollNo)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "Student [rollNo=" + this.rollNo + ", firstName=" + this.firstName + ", lastName=" + this.lastName
                    + ", nickName=" + this.nickName + ", marks=" + this.marks + "]";
        }

    }

    static interface SaxEvent {};

    static class StartElement
    implements SaxEvent
    {
        final String uri;
        final String localName;
        final String qName;
        final Attributes attributes;

        /**
         * Constructor.
         *
         * @param uri
         * @param localName
         * @param qName
         * @param attributes
         */
        protected StartElement(String uri, String localName, String qName, Attributes attributes) {
            this.uri = uri;
            this.localName = localName;
            this.qName = qName;
            this.attributes = attributes;
        }
    }

    static class EndElement
    implements SaxEvent
    {
        final String uri;
        final String localName;
        final String qName;

        /**
         * Constructor.
         *
         * @param uri
         * @param localName
         * @param qName
         */
        protected EndElement(String uri, String localName, String qName) {
            this.uri = uri;
            this.localName = localName;
            this.qName = qName;
        }
    }

    static class Characters
    implements SaxEvent
    {
        //final char[] ch;
        //final int start;
        //final int length;
        final String str;

        /**
         * Constructor.
         *
         * @param ch
         * @param start
         * @param length
         */
        protected Characters(
                //char[] ch, int start, int length
                final String str )
        {
            //this.ch = ch;
            //this.start = start;
            //this.length = length;
            this.str = str;
        }
    }

    static class CoroSaxhandler
    extends DefaultHandler
    {
        final Coroutine<Void, SaxEvent> coroutine;

        /**
         * Constructor.
         *
         * @param coroutine
         */
        protected CoroSaxhandler(
                final Coroutine<Void, SaxEvent> coroutine )
        {
            this.coroutine = coroutine;
        }

        /* (non-Javadoc)
         * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException
        {
            // TODO Auto-generated method stub
            //super.startElement(uri, localName, qName, attributes);
            coroutine.resume(
                    new StartElement( uri, localName, qName, attributes) );
        }

        /* (non-Javadoc)
         * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
         */
        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException
        {
            // TODO Auto-generated method stub
            //super.endElement(uri, localName, qName);
            coroutine.resume(
                    new EndElement( uri, localName, qName) );
        }

        /* (non-Javadoc)
         * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
         */
        @Override
        public void characters(
                char[] ch ,
                int start ,
                int length )
                        throws SAXException
        {
            // TODO Auto-generated method stub
            //super.characters(ch, start, length);
            //coroutine.resume( new Characters(ch, start, length) );
            coroutine.resume(
                    new Characters( new String( ch , start , length ) ) );
        }

    }

    static class NewStudent
    extends NoVariablesNoArgumentsExpression<Student>
    {

        @Override
        public Student evaluate(
                final HasArgumentsAndVariables parent )
        {
            return new Student();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Class<? extends Student>[] type()
        {
            return new Class[] { Student.class };
        }

    }

    //static class SetNewStudentStmt
    //extends SimpleStepWithoutExpression<Student>
    //{
    //    /* (non-Javadoc)
    //     * @see de.heinerkuecker.coroutine.CoroCheckable#checkUseVariables(java.util.HashSet, de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep, java.util.Map, java.util.Map)
    //     */
    //    @Override
    //    public void checkUseVariables(HashSet<String> alreadyCheckedProcedureNames,
    //            CoroutineOrProcedureOrComplexstep<?, ?> parent, Map<String, Class<?>> globalVariableTypes,
    //            Map<String, Class<?>> localVariableTypes) {
    //        // TODO Auto-generated method stub
    //
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see de.heinerkuecker.coroutine.step.simple.SimpleStep#execute(de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep)
    //     */
    //    @Override
    //    public CoroIterStepResult<Student> execute(CoroutineOrProcedureOrComplexstep<Student> parent) {
    //        // TODO Auto-generated method stub
    //        return null;
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see de.heinerkuecker.coroutine.step.CoroIterStep#setResultType(java.lang.Class)
    //     */
    //    @Override
    //    public void setResultType(Class<? extends Student> resultType) {
    //        // TODO Auto-generated method stub
    //
    //    }
    //}

    static class SetNewStudentStmt
    extends SetLocalVar<
    /*RESULT*/Void ,
    /*RESUME_ARGUMENT*/SaxEvent ,
    Student>
    {
        /**
         * Constructor.
         */
        public SetNewStudentStmt()
        {
            super(
                    //localVarName
                    "student" ,
                    //varValue
                    new Student() );
        }
    }

    static class SetStudentFirstname
    extends AbstrLocalVarUseWithExpressionStmt<
    /*RESULT*/Void ,
    /*RESUME_ARGUMENT*/Characters ,
    /*VARIABLE*/Student ,
    /*EXPRESSION*/Characters >
    {
        /**
         * Constructor.
         */
        public SetStudentFirstname()
        {
            super(
                    //localVarName
                    "student" ,
                    //expression
                    // get characters form resume arguments
                    new GetResumeArgument<Characters>(
                            //type
                            Characters.class ) );
        }

        @Override
        protected void execute(
                final Student student ,
                final Characters characters )
        {
            student.firstName = characters.str;
        }

        @Override
        protected String opString()
        {
            return "firstName = ( (Characters) ResumeArgument )";
        }
    }

    @Test
    public void testSaxParser()
            throws Exception
    {
        final List<Student> students = new ArrayList<>();

        final Coroutine<Void, SaxEvent> coro =
                new Coroutine<>(
                        //resultType
                        Void.class ,
                        //steps
                        new DeclareVariable<>(
                                "students" ,
                                students ) ,
                        new While<>(
                                //condition
                                new InstanceOf(
                                        //valueExpression
                                        new GetResumeArgument<>(
                                                //type
                                                SaxEvent.class ) ,
                                        //type
                                        StartElement.class ) ) ,
                        //steps
                        new YieldReturn<>( NullValue.nullValue() ) ,
                        new While<Void, SaxEvent>(
                                //condition: ! endElement student
                                new Not(
                                        new InstanceOf(
                                                //valueExpression
                                                new GetResumeArgument<>(
                                                        //type
                                                        SaxEvent.class ) ,
                                                //type
                                                EndElement.class ) ) ,
                                //steps
                                new DeclareVariable<>(
                                        // varName
                                        "student" ,
                                        // value
                                        new NewStudent() ) ,
                                new AddToCollectionLocalVar<>(
                                        // listLocalVarName
                                        "students" ,
                                        // elementToAddExpression
                                        new GetLocalVar<>(
                                                //localVarName
                                                "student" ,
                                                // type
                                                Student.class ) ) ,
                                new SetStudentFirstname() ) );

                try
                {
                    final ByteArrayInputStream input = new ByteArrayInputStream( XML_STR.getBytes() );
                    final SAXParserFactory factory = SAXParserFactory.newInstance();
                    final SAXParser saxParser = factory.newSAXParser();
                    final CoroSaxhandler saxHandler = new CoroSaxhandler( coro );
                    saxParser.parse(
                            input ,
                            saxHandler );
                }
                catch ( Exception e )
                {
                    System.err.println( e.getMessage() );
                    e.printStackTrace();
                    throw e;
                }

                // TODO assert
                Assert.assertEquals(
                        //expected
                        3 ,
                        //actual
                        students.size() );

    }

}
