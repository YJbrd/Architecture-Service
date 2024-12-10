package View;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Welcome extends JFrame {

    private static final long serialVersionUID = -4939544011287453046L;
    
    JButton login = new JButton( "Me connecter" );
    JButton register = new JButton( "Créer un nouveau profil" );



    public Welcome() {
        super( "Bienvenue sur Joton !" );
        this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        this.setSize( 600, 400 );
        this.setLocationRelativeTo( null );

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new FlowLayout());
        contentPane.add( login );
        contentPane.add( register );
       
        
        login.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(() -> new FrameLogIn());
			}			   
		
        });
        register.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(() -> new FrameRegister());
			}			   
		
        });
        
    }
    
    public static void main(String[] args) throws Exception {
        // appliquer le "look and feel"
        UIManager.setLookAndFeel( new NimbusLookAndFeel() );
        
        // Lancer la fenêtre 
        Welcome myWindow = new Welcome();
        myWindow.setVisible( true );
    } 

}
