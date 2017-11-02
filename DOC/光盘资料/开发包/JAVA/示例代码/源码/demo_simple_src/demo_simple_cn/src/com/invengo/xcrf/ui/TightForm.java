package com.invengo.xcrf.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import com.invengo.xcrf.ui.dialog.ConfigurationDialog;

public class TightForm extends JDialog{

	private static final long serialVersionUID = 8042161480710551493L;
	private JTextArea textArea;
	private JPanel parent;
	private TightForm tightForm;

	public void setData(String data) {
		textArea.setText(data);
	}
	
	public static List<TightForm> forms = Collections.synchronizedList(new ArrayList<TightForm>());
	
	public static TightForm getTightFormDialog(JPanel parent)
	{
		for(TightForm form : forms)
		{
			if(form.getParent() == parent)
				return form;
		}
		return new TightForm(parent);
	}
	
	

	private TightForm(JPanel parent) {
		super(ConfigurationDialog.getInstance(),false);
		this.parent = parent;
		tightForm = this;
		
		setBounds(100, 100, 450, 197);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
//		setModal(true);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 442, 163);
		getContentPane().add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);

		scrollPane.setViewportView(textArea);
		
		
		forms.add(tightForm);
		
		setLocationRelativeTo(null);

		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				forms.remove(tightForm);
			}

		});

	}

	@Override
	public JPanel getParent() {
		return parent;
	}
}
