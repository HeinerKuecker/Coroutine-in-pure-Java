package de.heinerkuecker.coroutine.iterator.test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.heinerkuecker.coroutine.Coroutine;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.Function;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.arg.Argument;
import de.heinerkuecker.coroutine.arg.Parameter;
import de.heinerkuecker.coroutine.exprs.AbstrExprsUseExprs;
import de.heinerkuecker.coroutine.exprs.AbstrNoVarsNoArgsExpression;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.GetResumeArgument;
import de.heinerkuecker.coroutine.exprs.NewIllegalStateException;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.StrConcat;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.exprs.bool.And;
import de.heinerkuecker.coroutine.exprs.bool.CoroBooleanExpression;
import de.heinerkuecker.coroutine.exprs.bool.Equals;
import de.heinerkuecker.coroutine.exprs.bool.InstanceOf;
import de.heinerkuecker.coroutine.exprs.bool.Not;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.complex.FunctionCall;
import de.heinerkuecker.coroutine.stmt.complex.IfElse;
import de.heinerkuecker.coroutine.stmt.complex.While;
import de.heinerkuecker.coroutine.stmt.flow.Throw;
import de.heinerkuecker.coroutine.stmt.ret.FunctionReturn;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturnVoid;
import de.heinerkuecker.coroutine.stmt.simple.AbstrLocalVarUseWithExpressionStmt;
import de.heinerkuecker.coroutine.stmt.simple.AddToCollectionLocalVar;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;
import de.heinerkuecker.util.StringUtil;

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
            "</class>";

    /**
     *
     * Student.
     */
    static class Student
    {
        String rollno;
        String firstname;
        String lastname;
        String nickname;
        String marks;

        /**
         * generated
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.firstname == null) ? 0 : this.firstname.hashCode());
            result = prime * result + ((this.lastname == null) ? 0 : this.lastname.hashCode());
            result = prime * result + ((this.marks == null) ? 0 : this.marks.hashCode());
            result = prime * result + ((this.nickname == null) ? 0 : this.nickname.hashCode());
            result = prime * result + ((this.rollno == null) ? 0 : this.rollno.hashCode());
            return result;
        }

        /**
         * generated
         */
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
            if (this.firstname == null) {
                if (other.firstname != null) {
                    return false;
                }
            } else if (!this.firstname.equals(other.firstname)) {
                return false;
            }
            if (this.lastname == null) {
                if (other.lastname != null) {
                    return false;
                }
            } else if (!this.lastname.equals(other.lastname)) {
                return false;
            }
            if (this.marks == null) {
                if (other.marks != null) {
                    return false;
                }
            } else if (!this.marks.equals(other.marks)) {
                return false;
            }
            if (this.nickname == null) {
                if (other.nickname != null) {
                    return false;
                }
            } else if (!this.nickname.equals(other.nickname)) {
                return false;
            }
            if (this.rollno == null) {
                if (other.rollno != null) {
                    return false;
                }
            } else if (!this.rollno.equals(other.rollno)) {
                return false;
            }
            return true;
        }

        /**
         * generated
         */
        @Override
        public String toString() {
            return "Student [rollno=" + this.rollno + ", firstname=" + this.firstname + ", lastname=" + this.lastname
                    + ", nickname=" + this.nickname + ", marks=" + this.marks + "]";
        }

    }

    /**
     * Common type of resume argument.
     */
    static interface SaxEvent {};

    /**
     * Abstract super class for sax events
     * with name of XML element.
     */
    abstract static class SaxEventWithElementname
    implements SaxEvent
    {
        final String qName;

        /**
         * Constructor.
         *
         * @param qName
         */
        protected SaxEventWithElementname(
                final String qName )
        {
            this.qName = qName;
        }

        final String getElementname()
        {
            return qName;
        }
    }

    /**
     * Start XML element sax event.
     */
    static class StartElement
    extends SaxEventWithElementname
    {
        final Attributes attributes;

        /**
         * Constructor.
         *
         * @param qName
         * @param attributes
         */
        StartElement(
                String qName,
                Attributes attributes)
        {
            super( qName );
            this.attributes = attributes;
        }

        @Override
        public String toString()
        {
            final StringBuilder attributesBuff = new StringBuilder();
            for ( int index = 0 ; index < this.attributes.getLength() ; index++ )
            {
                if ( attributesBuff.length() > 0 )
                {
                    attributesBuff.append( " " );
                }

                attributesBuff.append( attributes.getQName( index ) );
                attributesBuff.append( "=" );
                attributesBuff.append( StringUtil.strAsJavaLiteral( attributes.getValue( index ) ) );
            }

            return
                    "StartElement[" +
                    "qName=" + this.qName + ", " +
                    "attributes=[" + attributesBuff + "]]";
        }
    }

    /**
     * End XML element sax event.
     */
    static class EndElement
    extends SaxEventWithElementname
    {
        /**
         * Constructor.
         *
         * @param qName
         */
        EndElement(
                String qName )
        {
            super( qName );
        }

        @Override
        public String toString()
        {
            return
                    "EndElement[" +
                    "qName=" + this.qName +
                    "]";
        }
    }

    /**
     * Characters sax event.
     */
    static class Characters
    implements SaxEvent
    {
        final String str;

        /**
         * Constructor.
         *
         * @param str
         */
        protected Characters(
                final String str )
        {
            this.str = str;
        }

        @Override
        public String toString()
        {
            return
                    "Characters " +
                    StringUtil.strAsJavaLiteral( this.str );
        }
    }

    /**
     * Sax event handler.
     */
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

        @Override
        public void startElement(
                String uri ,
                String localName ,
                String qName ,
                Attributes attributes )
        throws SAXException
        {
            final StartElement startElement = new StartElement( qName , attributes );

            System.out.println( startElement );

            coroutine.resume(
                    startElement );

            System.out.println( coroutine );
        }

        @Override
        public void endElement(
                String uri ,
                String localName ,
                String qName )
        throws SAXException
        {
            final EndElement endElement = new EndElement( qName );

            System.out.println( endElement );

            coroutine.resume(
                    endElement );

            System.out.println( coroutine );
        }

        @Override
        public void characters(
                char[] ch ,
                int start ,
                int length )
        throws SAXException
        {
            final Characters characters = new Characters( new String( ch , start , length ) );

            System.out.println( characters );

            coroutine.resume(
                    characters );

            System.out.println( coroutine );
        }
    }

    /**
     * Coroutine expression to create new {@link Student} object.
     */
    static class NewStudent
    extends AbstrNoVarsNoArgsExpression<Student , Void , SaxEvent>
    {
        @Override
        public Student evaluate(
                final HasArgumentsAndVariables<? extends SaxEvent> parent )
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

    /**
     * Coroutine statement to set an arbitrary member of an {@link Student} object from appropriate XML element.
     */
    static class SetStudentField
    extends AbstrLocalVarUseWithExpressionStmt<
    /*FUNCTION_RETURN*/Void ,
    /*COROUTINE_RETURN*/Void ,
    /*RESUME_ARGUMENT*//*Characters*/SaxEvent ,
    /*VARIABLE*/Student ,
    /*EXPRESSION*//*Characters*/SaxEvent >
    {
        public final SimpleExpression<? extends String , Void , /*Characters*/SaxEvent> fieldNameExpression;

        /**
         * Constructor.
         */
        SetStudentField(
                final SimpleExpression<? extends String , Void , /*Characters*/SaxEvent> fieldNameExpression )
        {
            super(
                    //localVarName
                    "student" ,
                    //expression
                    // get characters form resume arguments
                    new GetResumeArgument</*Characters*/SaxEvent , Void>(
                            //type
                            //Characters.class
                            ) );

            this.fieldNameExpression = fieldNameExpression;
        }

        @Override
        protected void execute(
                final CoroutineOrFunctioncallOrComplexstmt<Void, Void, /*Characters*/SaxEvent> parent ,
                final Student student ,
                final /*Characters*/SaxEvent saxEvent )
        {
            final String fieldName =
                    fieldNameExpression.evaluate( parent );

            final Characters characters = (Characters) saxEvent;


            switch ( fieldName )
            {
            case "firstname":
                student.firstname = characters.str;
                break;

            case "lastname":
                student.lastname = characters.str;
                break;

            case "nickname":
                student.nickname = characters.str;
                break;

            case "marks":
                student.marks = characters.str;
                break;

            default:
                throw new IllegalArgumentException( fieldName );
                //break;
            }
        }

        @Override
        protected String opString()
        {
            return
                    this.getClass().getSimpleName() + " " +
                    this.fieldNameExpression + " = ( (Characters) ResumeArgument )";
        }
    }

    /**
     * Coroutine statement to set member {@link Student#rollno} of an {@link Student} object from start XML element attributes.
     */
    static class SetStudentRollno
    extends AbstrLocalVarUseWithExpressionStmt<
    /*FUNCTION_RETURN*/Void ,
    /*COROUTINE_RETURN*/Void ,
    /*RESUME_ARGUMENT*/StartElement ,
    /*VARIABLE*/Student ,
    /*EXPRESSION*/StartElement >
    {
        /**
         * Constructor.
         */
        SetStudentRollno()
        {
            super(
                    // localVarName
                    "student" ,
                    // expression
                    new GetResumeArgument<StartElement , Void>(
                            //type
                            //StartElement.class
                            ) );
        }

        @Override
        protected void execute(
                final CoroutineOrFunctioncallOrComplexstmt<Void, Void, StartElement> parent ,
                final Student student ,
                final StartElement startElement )
        {
            final String rollno = startElement.attributes.getValue( "rollno" );

            student.rollno = rollno;
        }

        @Override
        protected String opString()
        {
            return "student.rollno = startElement attribute rollno";
        }
    }

    /**
     * Test SAX parser.
     *
     * @throws Exception
     */
    @Test
    public void testSaxParser()
            throws Exception
    {
        final AbstrExprsUseExprs getXmlElementNameFromResumeArgumentRaw =
                new AbstrExprsUseExprs<String, SaxEventWithElementname , Void , SaxEventWithElementname>(
                        // type
                        String.class ,
                        // argumentExpression
                        new GetResumeArgument</*RESUME_ARGUMENT*/SaxEventWithElementname , /*COROUTINE_RETURN*/ Void>(
                                // type
                                //SaxEventWithElementname.class
                                ) ) {

            @Override
            public String execute(
                    final SaxEventWithElementname startElement )
            {
                return startElement.getElementname();
            }

            @Override
            public String toString()
            {
                return "ResumeArgument element.getElementname()";
            }
        };

        final AbstrExprsUseExprs<String , SaxEventWithElementname , Void , SaxEvent> getXmlElementNameFromResumeArgument =
                getXmlElementNameFromResumeArgumentRaw;

        // check is start XML element class: <class>
        final CoroBooleanExpression isStartElementClass =
                new And(
                        new InstanceOf(
                                // valueExpression
                                new GetResumeArgument<>(
                                        // type
                                        //SaxEvent.class
                                        ) ,
                                // type
                                StartElement.class ) ,
                        new Equals<>(
                                getXmlElementNameFromResumeArgument ,
                                "class" ) );

        // function to check is start XML element class and consume it
        final Function</*FUNCTION_RETURN*/ Boolean, /*COROUTINE_RETURN*/ Void, /*RESUME_ARGUMENT*/ SaxEvent> checkIsStartElementClassAndConsumeIt =
                new Function<>(
                        // name
                        "checkIsStartElementClassAndConsumeIt" ,
                        // params
                        null ,
                        // bodyStmts
                        new IfElse<>(
                                // condition
                                isStartElementClass ,
                                // thenStmts
                                new CoroStmt[] {
                                        // consume start xml element class
                                        new YieldReturnVoid<>() ,
                                        new FunctionReturn</*FUNCTION_RETURN*/ Boolean, /*COROUTINE_RETURN*/ Void, /*RESUME_ARGUMENT*/ SaxEvent>(
                                                // functionReturnType
                                                Boolean.class ,
                                                // value
                                                true )
                                } ,
                                // elseStmts
                                new CoroStmt[] {
                                        new FunctionReturn</*FUNCTION_RETURN*/ Boolean, /*COROUTINE_RETURN*/ Void, /*RESUME_ARGUMENT*/ SaxEvent>(
                                                // functionReturnType
                                                Boolean.class ,
                                                // value
                                                false )
                                } ) );

        // function to consume white space between xml elements (actually consume all characters)
        final Function<Void, Void , SaxEvent> consumeWhitespaces =
                new Function<Void, Void , SaxEvent>(
                        // name
                        "consumeWhitespaces" ,
                        // params
                        null ,
                        // bodyStmts
                        new While<>(
                                // condition
                                new InstanceOf<>(
                                        // valueExpression
                                        new GetResumeArgument<>(
                                                // type
                                                //SaxEvent.class
                                                ) ,
                                        // type
                                        Characters.class ) ,
                                // stmts
                                new YieldReturnVoid<>() ) );

        // function to consume end xml element
        final Function</*FUNCTION_RETURN*/ Void, /*COROUTINE_RETURN*/ Void, /*RESUME_ARGUMENT*/ SaxEvent> consumeEndElement =
                new Function<Void, Void , SaxEvent>(
                        // name
                        "consumeEndElement" ,
                        // params
                        new Parameter[] {
                                new Parameter(
                                        // name
                                        "name" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        String.class )
                        } ,
                        //bodyStmts
                        new FunctionCall<>(
                                // functionName
                                "consumeWhitespaces" ,
                                //functionReturnType
                                Void.class ) ,
                        new IfElse</*FUNCTION_RETURN*/ Void, /*COROUTINE_RETURN*/ Void, /*RESUME_ARGUMENT*/ SaxEvent>(
                                // condition
                                new And<>(
                                        new InstanceOf<>(
                                                // valueExpression
                                                new GetResumeArgument<>(
                                                        // type
                                                        //SaxEvent.class
                                                        ) ,
                                                // type
                                                EndElement.class ) ,
                                        new Equals<>(
                                                getXmlElementNameFromResumeArgument ,
                                                new GetFunctionArgument<>(
                                                        // functionArgumentName
                                                        "name" ,
                                                        // type
                                                        String.class ) ) ) ,
                                // thenStmts
                                new CoroStmt[] {
                                        // consume end xml element
                                        new YieldReturnVoid<>()
                                } ,
                                // elseStmts
                                new CoroStmt[] {
                                        new Throw<Void, Void , SaxEvent>(
                                                new Value<>(
                                                        new IllegalStateException(
                                                                "xml element end expected: " +
                                                                        new GetFunctionArgument<>(
                                                                                //functionArgumentName
                                                                                "name" ,
                                                                                //type
                                                                                String.class ) ) ) )
                                } ) );

        final Function<Void, Void , SaxEvent> readRollnoAttrFromStartXmlElement =
                new Function<>(
                        // name
                        "readRollnoAttrFromStartXmlElement" ,
                        // params
                        null ,
                        // bodyStmts
                        new IfElse</*FUNCTION_RETURN*/ Void, /*COROUTINE_RETURN*/ Void, /*RESUME_ARGUMENT*/ SaxEvent>(
                                // condition
                                new And<>(
                                        new InstanceOf<>(
                                                // valueExpression
                                                new GetResumeArgument<>(
                                                        // type
                                                        //SaxEvent.class
                                                        ) ,
                                                // type
                                                StartElement.class ) ,
                                        new Equals<>(
                                                getXmlElementNameFromResumeArgument ,
                                                "student" ) ) ,
                                // thenStmts
                                new CoroStmt[] {
                                        new SetStudentRollno() ,
                                        // consume start xml element
                                        new YieldReturnVoid<>()
                                } ,
                                // elseStmts
                                new CoroStmt[] {
                                        new Throw<Void , Void , SaxEvent>(
                                                new NewIllegalStateException(
                                                        new Value<>( "xml element start expected student" ) ) )
                                } ) );


        // function to consume start xml element, text and end xml element with the specified element name
        final Function<Void , Void , SaxEvent> readXmlElement =
                new Function<Void , Void , SaxEvent>(
                        // name
                        "readXmlElement" ,
                        // params
                        new Parameter[] {
                                new Parameter(
                                        // name
                                        "name" ,
                                        // isMandantory
                                        true ,
                                        // type
                                        String.class )
                        } ,
                        // bodyStmts
                        new FunctionCall<>(
                                // functionName
                                "consumeWhitespaces" ,
                                // functionReturnType
                                Void.class ) ,
                        new IfElse</*FUNCTION_RETURN*/ Void, /*COROUTINE_RETURN*/ Void, /*RESUME_ARGUMENT*/ SaxEvent>(
                                // condition
                                new And<>(
                                        new InstanceOf<>(
                                                // valueExpression
                                                new GetResumeArgument<>(
                                                        //type
                                                        //SaxEvent.class
                                                        ) ,
                                                // type
                                                StartElement.class ) ,
                                        new Equals<>(
                                                getXmlElementNameFromResumeArgument ,
                                                new GetFunctionArgument<>(
                                                        // functionArgumentName
                                                        "name" ,
                                                        // type
                                                        String.class ) ) ) ,
                                // thenStmts
                                new CoroStmt[] {
                                        // consume start xml element
                                        new YieldReturnVoid<>()
                                } ,
                                // elseStmts
                                new CoroStmt[] {
                                        new Throw<Void , Void , SaxEvent>(
                                                new NewIllegalStateException(
                                                        new StrConcat(
                                                                "xml element start expected: " ,
                                                                new GetFunctionArgument<>(
                                                                        //functionArgumentName
                                                                        "name" ,
                                                                        //type
                                                                        String.class ) ) ) )
                                } ) ,
                        // set text
                        new SetStudentField(
                                // fieldName
                                new GetFunctionArgument<>(
                                        // functionArgumentName
                                        "name" ,
                                        // type
                                        String.class ) ) ,
                        // consume resume call
                        new YieldReturnVoid<>() ,
                        new FunctionCall<>(
                                // functionName
                                "consumeEndElement" ,
                                // functionReturnType
                                Void.class ,
                                // args
                                new Argument<String , Void, SaxEvent>(
                                        // name
                                        "name" ,
                                        // expression
                                        new GetFunctionArgument<>(
                                                // functionArgumentName
                                                "name" ,
                                                // type
                                                String.class ) ) )
                        );

        final List<Student> students = new ArrayList<>();

        final Coroutine<Void, SaxEvent> coroutine =
                new Coroutine<Void , SaxEvent>(
                        // coroutineReturnType
                        Void.class ,
                        // resumeArgumentType
                        SaxEvent.class ,
                        // functions
                        Arrays.asList(
                                checkIsStartElementClassAndConsumeIt ,
                                consumeWhitespaces ,
                                readRollnoAttrFromStartXmlElement ,
                                readXmlElement ,
                                consumeEndElement ) ,
                        // params
                        null ,
                        // args
                        null ,
                        // globalVariableDeclarations
                        null ,
                        // stmts
                        new DeclareVariable<>(
                                "students" ,
                                students ) ,
                        new While<>(
                                // condition
                                //isStartElementClass
                                new FunctionCall<>(
                                        // functionName
                                        "checkIsStartElementClassAndConsumeIt" ,
                                        // functionReturnType
                                        Boolean.class ) ,
                                // stmts
                                new FunctionCall<>(
                                        // functionName
                                        "consumeWhitespaces" ,
                                        // functionReturnType
                                        Void.class ) ,
                                new While<Void, Void, SaxEvent>(
                                        // condition: ! endElement student
                                        new Not<>(
                                                new InstanceOf<>(
                                                        // valueExpression
                                                        new GetResumeArgument<>(
                                                                // type
                                                                //SaxEvent.class
                                                                ) ,
                                                        // type
                                                        EndElement.class ) ) ,
                                        // stmts
                                        new DeclareVariable</*VARIABLE*/Student , /*FUNCTION_RETURN*/ Void , /*COROUTINE_RETURN*/ Void , /*RESUME_ARGUMENT*/ SaxEvent>(
                                                // varName
                                                "student" ,
                                                // type
                                                Student.class ,
                                                // initialVarValueExpression
                                                new NewStudent() ) ,
                                        new AddToCollectionLocalVar<>(
                                                // listLocalVarName
                                                "students" ,
                                                // elementToAddExpression
                                                new GetLocalVar<>(
                                                        // localVarName
                                                        "student" ,
                                                        // type
                                                        Student.class ) ) ,
                                        new FunctionCall<>(
                                                // functionName
                                                "readRollnoAttrFromStartXmlElement" ,
                                                // functionReturnType
                                                Void.class ) ,
                                        new FunctionCall<>(
                                                // functionName
                                                "readXmlElement" ,
                                                // functionReturnType
                                                Void.class ,
                                                // args
                                                new Argument<String , Void , SaxEvent>(
                                                        // name
                                                        "name" ,
                                                        // value
                                                        "firstname" ) ) ,
                                        new FunctionCall<>(
                                                // functionName
                                                "readXmlElement" ,
                                                // functionReturnType
                                                Void.class ,
                                                // args
                                                new Argument<String , Void , SaxEvent>(
                                                        // name
                                                        "name" ,
                                                        // value
                                                        "lastname" ) ) ,
                                        new FunctionCall<>(
                                                // functionName
                                                "readXmlElement" ,
                                                // functionReturnType
                                                Void.class ,
                                                // args
                                                new Argument<String , Void , SaxEvent>(
                                                        // name
                                                        "name" ,
                                                        // value
                                                        "nickname" ) ) ,
                                        new FunctionCall<>(
                                                // functionName
                                                "readXmlElement" ,
                                                // functionReturnType
                                                Void.class ,
                                                // args
                                                new Argument<String , Void , SaxEvent>(
                                                        // name
                                                        "name" ,
                                                        // value
                                                        "marks" ) ) ,
                                        new FunctionCall<>(
                                                // functionName
                                                "consumeEndElement" ,
                                                // functionReturnType
                                                Void.class ,
                                                // args
                                                new Argument<String , Void , SaxEvent>(
                                                        // name
                                                        "name" ,
                                                        // value
                                                        "student" ) ) ,
                                        new FunctionCall<>(
                                                // functionName
                                                "consumeWhitespaces" ,
                                                // functionReturnType
                                                Void.class ) ) ) );

        try
        {
            final ByteArrayInputStream input = new ByteArrayInputStream( XML_STR.getBytes() );
            final SAXParserFactory factory = SAXParserFactory.newInstance();
            final SAXParser saxParser = factory.newSAXParser();
            final CoroSaxhandler saxHandler = new CoroSaxhandler( coroutine );
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

        final List<Student> expected =
                new ArrayList<>();

        {
            final Student student = new Student();

            student.rollno = "393";
            student.firstname = "dinkar";
            student.lastname = "kad";
            student.nickname = "dinkar";
            student.marks = "85";

            expected.add( student );
        }

        {
            final Student student = new Student();

            student.rollno = "493";
            student.firstname = "Vaneet";
            student.lastname = "Gupta";
            student.nickname = "vinni";
            student.marks = "95";

            expected.add( student );
        }

        {
            final Student student = new Student();

            student.rollno = "593";
            student.firstname = "jasvir";
            student.lastname = "singn";
            student.nickname = "jazz";
            student.marks = "90";

            expected.add( student );
        }

        Assert.assertEquals(
                expected ,
                //actual
                students );
    }

}
