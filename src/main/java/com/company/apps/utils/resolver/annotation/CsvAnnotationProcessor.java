package com.company.apps.utils.resolver.annotation;

import javax.annotation.processing.*;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.Element;

import com.google.auto.service.AutoService;

import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_14)
public class CsvAnnotationProcessor extends AbstractProcessor {

    protected static final String ERROR_FIELD_ANNOTATION = "All fields of CsvEntity classes must be annotated with @CsvColumn or @CsvNestedEntity or @SkipCsvValidation";
    protected static final String ERROR_NAME_FOR_PARSING = "The 'nameForParsing' parameter must be specified and not be empty";
    protected static final String ERROR_IDENTITY_FIELD = "There must be exactly one field annotated with @IdEntity";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv
                .getElementsAnnotatedWith(CsvEntity.class)
                .stream()
                .filter(element -> element.getKind() == ElementKind.CLASS)
                .forEach(this::processElement);
        return true;
    }

    private void processElement(Element element) {
        long idFields = element.getEnclosedElements()
                .stream()
                .filter(enclosedElement -> enclosedElement.getKind() == ElementKind.FIELD)
                .filter(enclosedElement -> enclosedElement.getAnnotation(SkipCsvValidation.class) == null)
                .peek(this::validateField)
                .filter(enclosedElement -> enclosedElement.getAnnotation(IdEntity.class) != null)
                .count();

        if (idFields != 1) {
            processingEnv
                    .getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, ERROR_IDENTITY_FIELD, element);
        }
    }

    private void validateField(Element enclosedElement) {
        CsvNestedEntity csvNestedEntity = enclosedElement.getAnnotation(CsvNestedEntity.class);
        CsvColumn csvColumn = enclosedElement.getAnnotation(CsvColumn.class);

        if (csvColumn == null && csvNestedEntity == null) {
            processingEnv
                    .getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, ERROR_FIELD_ANNOTATION, enclosedElement);

        } else if (csvColumn != null && csvColumn.nameForParsing().isEmpty()) {
            processingEnv
                    .getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, ERROR_NAME_FOR_PARSING, enclosedElement);
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(
                SkipCsvValidation.class.getName(),
                CsvNestedEntity.class.getName(),
                CsvEntity.class.getName(),
                CsvColumn.class.getName(),
                IdEntity.class.getName()
        );
    }
}