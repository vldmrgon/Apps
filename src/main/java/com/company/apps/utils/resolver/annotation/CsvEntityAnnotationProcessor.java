package com.company.apps.utils.resolver.annotation;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.Processor;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.Element;

import com.google.auto.service.AutoService;

import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Processor.class)
public class CsvEntityAnnotationProcessor extends AbstractProcessor {

    private static final String ERROR_FIELD_ANNOTATION = "All fields of CsvEntity classes must be annotated with @CsvColumn or @CsvNestedEntity";
    private static final String ERROR_NAME_FOR_PARSING = "The 'nameForParsing' parameter must be specified and not be empty";
    private static final String ERROR_IDENTITY_FIELD = "There must be exactly one field annotated with @IdEntity";
    private static final String ERROR_CSV_ENTITY_DEFINITION = "Error in CsvEntity definition";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(CsvEntity.class)) {

            if (element.getKind() == ElementKind.CLASS) {

                boolean allFieldsCorrect = true;
                int idFields = 0;

                for (Element enclosedElement : element.getEnclosedElements()) {

                    if (enclosedElement.getKind() == ElementKind.FIELD) {

                        CsvColumn csvColumnAnnotation = enclosedElement.getAnnotation(CsvColumn.class);

                        if (csvColumnAnnotation == null && enclosedElement.getAnnotation(CsvNestedEntity.class) == null) {
                            allFieldsCorrect = false;

                            processingEnv
                                    .getMessager()
                                    .printMessage(Diagnostic.Kind.ERROR, ERROR_FIELD_ANNOTATION, enclosedElement);

                        } else if (csvColumnAnnotation != null) {
                            String nameForParsing = csvColumnAnnotation.nameForParsing();

                            if (nameForParsing.isEmpty()) {
                                allFieldsCorrect = false;

                                processingEnv
                                        .getMessager()
                                        .printMessage(Diagnostic.Kind.ERROR, ERROR_NAME_FOR_PARSING, enclosedElement);
                            }
                        }

                        if (enclosedElement.getAnnotation(IdEntity.class) != null) {
                            idFields++;
                        }
                    }
                }

                if (idFields != 1) {
                    processingEnv
                            .getMessager()
                            .printMessage(Diagnostic.Kind.ERROR, ERROR_IDENTITY_FIELD, element);
                }

                if (!allFieldsCorrect || idFields != 1) {
                    processingEnv
                            .getMessager()
                            .printMessage(Diagnostic.Kind.ERROR, ERROR_CSV_ENTITY_DEFINITION, element);
                }
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(
                CsvNestedEntity.class.getName(),
                CsvEntity.class.getName(),
                CsvColumn.class.getName(),
                IdEntity.class.getName()
        );
    }
}