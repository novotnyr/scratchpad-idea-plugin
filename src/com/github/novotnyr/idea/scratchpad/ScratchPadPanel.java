package com.github.novotnyr.idea.scratchpad;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.DocumentAdapter;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class ScratchPadPanel extends SimpleToolWindowPanel {
    public static final String PROPERTIES_TEXT_KEY = "com.github.novotnyr.idea.scratchpad#scratchPadText";

    private final Project project;

    private final JTextArea textArea = new JTextArea();

    public ScratchPadPanel(Project project) {
        super(true);
        this.project = project;

        add(this.textArea);

        String persistedTextAreaContent = getPropertiesComponent().getValue(PROPERTIES_TEXT_KEY, "");
        this.textArea.setText(persistedTextAreaContent);
        this.textArea.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(DocumentEvent documentEvent) {
                try {
                    Document document = documentEvent.getDocument();
                    String text = document.getText(0, document.getLength());
                    ScratchPadPanel.this.onTextChanged(text);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onTextChanged(String text) {
        getPropertiesComponent().setValue(PROPERTIES_TEXT_KEY, this.textArea.getText());
    }

    private PropertiesComponent getPropertiesComponent() {
        return PropertiesComponent.getInstance(this.project);
    }

}
