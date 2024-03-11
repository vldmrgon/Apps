package com.company.apps.utils.resolver.annotation;

import com.google.testing.compile.CompilationSubject;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compiler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;

class CsvAnnotationProcessorTest {

    @Test
    @DisplayName("Using all correct annotations: @IdEntity, @CsvColumn, @CsvNestedEntity, @SkipCsvValidation")
    void csvEntityWithNoErrorsTest() {
        JavaFileObject validSource = JavaFileObjects
                .forSourceLines("com.example.Player", "package com.example;",

                        "import com.company.apps.utils.resolver.annotation.SkipCsvValidation;",
                        "import com.company.apps.utils.resolver.annotation.CsvNestedEntity;",
                        "import com.company.apps.utils.resolver.annotation.CsvEntity;",
                        "import com.company.apps.utils.resolver.annotation.CsvColumn;",
                        "import com.company.apps.utils.resolver.annotation.IdEntity;",

                        "@CsvEntity",
                        "public class Player {",

                        "    @IdEntity",
                        "    @CsvColumn(nameForParsing = \"playerID\")",
                        "    private String playerID;",

                        "    @CsvNestedEntity",
                        "    private String birthInfo;",

                        "    @CsvColumn(nameForParsing = \"nameFirst\", defaultValue = \"unknown\")",
                        "    private String nameFirst;",

                        "    @CsvColumn(nameForParsing = \"nameLast\", defaultValue = \"unknown\")",
                        "    private String nameLast;",

                        "    @CsvColumn(nameForParsing = \"nameGiven\", defaultValue = \"unknown\")",
                        "    private String nameGiven;",

                        "    @SkipCsvValidation",
                        "    private String careerInfo;",
                        "}"
                );

        Compilation compilation = getCompilation(validSource);

        CompilationSubject
                .assertThat(compilation)
                .succeededWithoutWarnings();
    }

    @Test
    @DisplayName("Fields aren't annotated correctly: first field without @CsvColumn, second field without an annotation")
    void csvEntityFieldAnnotationErrorTest() {
        JavaFileObject invalidSource = JavaFileObjects
                .forSourceLines("com.example.InvalidPlayer", "package com.example;",

                        "import com.company.apps.utils.resolver.annotation.CsvEntity;",
                        "import com.company.apps.utils.resolver.annotation.IdEntity;",

                        "@CsvEntity",
                        "public class InvalidPlayer {",

                        "    @IdEntity",
                        "    private String playerID;",

                        "    private String unannotatedField;",
                        "}"
                );

        Compilation compilation = getCompilation(invalidSource);

        CompilationSubject
                .assertThat(compilation)
                .hadErrorContaining(CsvAnnotationProcessor.ERROR_FIELD_ANNOTATION);
    }

    @Test
    @DisplayName("Error when 'nameForParsing' of @CsvColumn is empty")
    void csvEntityNameForParsingErrorTest() {
        JavaFileObject invalidSource = JavaFileObjects
                .forSourceLines("com.example.InvalidPlayer", "package com.example;",

                        "import com.company.apps.utils.resolver.annotation.CsvEntity;",
                        "import com.company.apps.utils.resolver.annotation.CsvColumn;",
                        "import com.company.apps.utils.resolver.annotation.IdEntity;",

                        "@CsvEntity",
                        "public class InvalidPlayer {",

                        "    @IdEntity",
                        "    @CsvColumn(nameForParsing = \"\")",
                        "    private String playerID;",
                        "}"
                );

        Compilation compilation = getCompilation(invalidSource);

        CompilationSubject
                .assertThat(compilation)
                .hadErrorContaining(CsvAnnotationProcessor.ERROR_NAME_FOR_PARSING);
    }

    @Test
    @DisplayName("Error when there are multiple or no @IdEntity fields")
    void csvEntityIdentityFieldErrorTest() {
        JavaFileObject invalidSource = JavaFileObjects
                .forSourceLines("com.example.InvalidPlayer", "package com.example;",

                        "import com.company.apps.utils.resolver.annotation.CsvEntity;",
                        "import com.company.apps.utils.resolver.annotation.CsvColumn;",
                        "import com.company.apps.utils.resolver.annotation.IdEntity;",

                        "@CsvEntity",
                        "public class InvalidPlayer {",

                        "    @IdEntity",
                        "    @CsvColumn(nameForParsing = \"playerID\")",
                        "    private String playerID;",

                        "    @IdEntity",
                        "    private String anotherID;",
                        "}"
                );

        Compilation compilation = getCompilation(invalidSource);

        CompilationSubject
                .assertThat(compilation)
                .hadErrorContaining(CsvAnnotationProcessor.ERROR_IDENTITY_FIELD);
    }

    private Compilation getCompilation(JavaFileObject validSource) {
        return Compiler
                .javac()
                .withProcessors(new CsvAnnotationProcessor())
                .compile(validSource);
    }
}