package ar.com.quetedebo.remote;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;

import ar.com.quetedebo.core.Debt;

import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;

public class HistoryUI extends JFrame {
	private static final long serialVersionUID = -5945493982984344112L;
	private Set<Debt> debts;
	private JPanel debtList;
	private JPanel topPanel;
	private JLabel transferedLbl;
	private float totalAmount = 0f;
	private Map<String, Float> topReceivers;

	public HistoryUI() {
		super("Payment History");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		
		JScrollPane debtScroll = new JScrollPane();
		getContentPane().add(debtScroll, BorderLayout.CENTER);
		setVisible(true);
		
		debtList = new JPanel();
		debtList.setLayout(new BoxLayout(debtList, BoxLayout.Y_AXIS));
		debtScroll.setViewportView(debtList);
		
		JPanel rightPanel = new JPanel();
		getContentPane().add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
		
		JLabel transferedTitle = new JLabel("Total Transferido");
		rightPanel.add(transferedTitle);
		
		JSeparator separator1 = new JSeparator();
		separator1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 5));
		rightPanel.add(separator1);
		
		transferedLbl = new JLabel("$0.00");
		transferedLbl.setFont(new Font("Tahoma", Font.PLAIN, 20));
		rightPanel.add(transferedLbl);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		rightPanel.add(verticalStrut);
		
		JLabel topTitle = new JLabel("Top Receptores");
		rightPanel.add(topTitle);

		JSeparator separator2 = new JSeparator();
		separator2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 5));
		rightPanel.add(separator2);
		
		topPanel = new JPanel();
		rightPanel.add(topPanel);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		
		debts = new HashSet<Debt>();
		topReceivers = new HashMap<String, Float>();
	}
	
	public void updateStats() {
		transferedLbl.setText("$" + totalAmount);

		topPanel.removeAll();
		List<String> topReceiversList = topReceivers.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).map(Map.Entry::getKey).limit(5).toList(); //<String, Float>
		for (String topReceiver : topReceiversList) {
			JLabel topReceiverLabel = new JLabel(topReceiver + ": $" + topReceivers.get(topReceiver));
			topPanel.add(topReceiverLabel);

			JProgressBar progressBar = new JProgressBar();
			topPanel.add(progressBar);
			progressBar.setValue((int) (topReceivers.get(topReceiver) / totalAmount * 100));
		}
		
		topPanel.revalidate();
		topPanel.repaint();
		
	}

	public void addDebt(List<Debt> debts) {
		this.debts.addAll(debts);
		for (Debt debt : debts) {
			JLabel debtLabel = new JLabel(debt.toString());
			debtList.add(debtLabel);
			System.out.println(debt.toString());
			totalAmount += debt.getAmount();

			float value = topReceivers.getOrDefault(debt.getMemberPayment(), 0f) + debt.getAmount();
			topReceivers.put(debt.getMemberPayment(), value);
		}
		debtList.revalidate();
		debtList.repaint();
		updateStats();
	}

}
