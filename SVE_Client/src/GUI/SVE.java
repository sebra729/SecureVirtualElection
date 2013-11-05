package GUI;

import java.awt.EventQueue;

import javax.net.ssl.SSLSocket;
import javax.swing.JFrame;

import backend.Client;
import backend.ClientPtc;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.UUID;

public class SVE {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	private UUID idNr = null, validNr = null;
	
	private InetAddress host;
	static final int DEFAULT_CLA_PORT = 8189;	//Client to CLA 8189
	static final int DEFAULT_CTF_PORT = 8190;	//Client to STF 8190

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SVE window = new SVE();
					window.frame.setVisible(true);
							
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SVE() {
		initialize();
	}
	
	public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
	
	public UUID getIdentificationNr(){
		return UUID.randomUUID();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 571, 348);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
		);
		
		final JTextArea textArea = new JTextArea();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		
		JLabel lblUsername = new JLabel("Username");
		
		JLabel lblPassword = new JLabel("Password");
		
		JRadioButton rdbtnCandidate = new JRadioButton("Candidate_1");
		
		JRadioButton rdbtnCandidate_1 = new JRadioButton("Candidate_2");
		
		final JButton btnLogin = new JButton("Login");
		
		
		JRadioButton rdbtnCandidate_2 = new JRadioButton("Candidate_3");
		
		final JButton btnSubmitVote = new JButton("Submit Vote");
		btnSubmitVote.setEnabled(false);
		
		final JButton btnVerifyVote = new JButton("Verify Vote");
		btnVerifyVote.setEnabled(false);
		
		final JButton btnLogOut = new JButton("Log out");
		btnLogOut.setEnabled(false);
		
		JRadioButton rdbtnCandidate_3 = new JRadioButton("Candidate_4");
		
		final ButtonGroup group = new ButtonGroup();
		group.add(rdbtnCandidate);
		group.add(rdbtnCandidate_1);
		group.add(rdbtnCandidate_2);
		group.add(rdbtnCandidate_3);
		
		
		
		JLabel lblWriteYourCredentials = new JLabel("Write your credentials ");
		
		JLabel lblChoseYourCandidate = new JLabel("Chose your candidate");
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUsername)
								.addComponent(lblPassword))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnCandidate_2)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(btnLogin)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnLogOut))
								.addComponent(rdbtnCandidate)
								.addComponent(rdbtnCandidate_1)
								.addComponent(passwordField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
								.addComponent(textField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
								.addComponent(rdbtnCandidate_3)
								.addComponent(lblWriteYourCredentials)
								.addComponent(lblChoseYourCandidate)))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnSubmitVote)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnVerifyVote)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(7)
					.addComponent(lblWriteYourCredentials)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsername))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPassword))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLogin)
						.addComponent(btnLogOut))
					.addGap(28)
					.addComponent(lblChoseYourCandidate)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnCandidate)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnCandidate_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnCandidate_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnCandidate_3)
					.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSubmitVote)
						.addComponent(btnVerifyVote))
					.addContainerGap())
		);
		
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
		
//		Connection to server
		final ClientPtc protocol = new ClientPtc();
		try{
			InetAddress.getLocalHost();		
		}catch (Exception e) {
			e.printStackTrace();
		}
		int portCLA = DEFAULT_CLA_PORT;
		int portCTF = DEFAULT_CTF_PORT;	
		
		Client CLFClient = new Client( host, portCTF );
		final SSLSocket CLFSocket = CLFClient.runCLA();
		textArea.append("CLA Socket connection open \n");
		
		Client CLAClient = new Client( host, portCLA );
		final SSLSocket CLASocket = CLAClient.runCLA();
		textArea.append("CLT Socket connection open \n");
		

		
		
//		Action 
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!textField.getText().equals("") && !String.valueOf(passwordField.getPassword()).equals("")){
					String credentials = textField.getText() + " " + String.valueOf(passwordField.getPassword());
	
					
					if(CLASocket != null){
						protocol.sendMessage(CLASocket, credentials);
					}else{
						textArea.append("Socket to CLA is Null \n");
					}
					
					String[] result = protocol.getMessage(CLASocket);
					if(result[0].equals("Accepted")){
						validNr = protocol.getVerificationNr(CLASocket);
						textArea.append("Your validation number \n" + validNr + "\n");
						idNr = getIdentificationNr();
						btnSubmitVote.setEnabled(true);
						btnLogin.setEnabled(false);
						btnLogOut.setEnabled(true);
					}else{
						textArea.append(result[0] + "\n");
					}
				}else{
					textArea.append("Type in credentials \n");
				}
			}
		});
		
		btnSubmitVote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getSelectedButtonText(group) != null){
					String vote = "vote " + validNr + " " + idNr + " " + getSelectedButtonText(group);
					textArea.append(vote + "\n");
					if(CLFSocket != null){
						protocol.sendMessage(CLFSocket, vote);
						textArea.append("You voted for: " + getSelectedButtonText(group) +"\n");
						String[] msg = protocol.getMessage(CLFSocket);
						for(int i = 0; i<msg.length ; i++){
							textArea.append(msg[i] + " ");
						}
						textArea.append("\n");
						btnSubmitVote.setEnabled(false);
						btnVerifyVote.setEnabled(true);
					}else{
						textArea.append("Socket to CTL is Null \n");
					}
				}else{
					textArea.append("Chose a candidate! \n");
				}
						
			}
		});
		
		btnVerifyVote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg = "validation " + validNr + " " + idNr;
				protocol.sendMessage(CLFSocket, msg);
				
				String[] result = protocol.getMessage(CLFSocket);
				for(int i = 0; i<result.length ; i++){
					textArea.append(result[i] + " ");
				}
				textArea.append("\n");
			}
		});
		
		
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				protocol.sendMessage(CLFSocket, "print");
				textArea.setText("");
				textField.setText("");
				passwordField.setText("");
				validNr = null;
				idNr = null;
				group.clearSelection();
				btnLogin.setEnabled(true);
			}
		});
	}
}
