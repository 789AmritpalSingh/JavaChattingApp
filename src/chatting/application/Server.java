package chatting.application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Server implements ActionListener {

    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();

    static DataOutputStream dos;

    static JFrame f= new JFrame(); // creating object to add frame statically ,
    // instead of extending JFrame class

    // all the code for the frame of the application is inside this constructor
    Server(){
        f.setLayout(null);

        JPanel p1 = new JPanel();// It is used to make separate panel inside frame
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);// setting the coordinates of bounds, at where we want
        // our panel to be placed at , here panel is at top left corner as x and y are 0
        p1.setLayout(null); // we set this as null , because otherwise set bounds function won't work back.setBounds
        // which is for setting image  on frame
        f.add(p1);// setting panel p1 on the frame


        // setting back arrow button for exiting application
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2); // we are setting image again in ImageIcon because we can't put image directly
        // in JLabel
        JLabel back = new JLabel(i3); // to set the image on the frame;
        back.setBounds(5,20,25,25);
        p1.add(back); // adding image to panel

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {// used for handling click of the mouse event
                System.exit(0);// frame will get closed when we click on arrow icon, because of mouse clicked event
            }
        });

        // Setting Image for the profile
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);

        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        // Setting Image for the video call option
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);

        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);

        // Setting Image for phone option
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);

        JLabel phone = new JLabel(i12);
        phone.setBounds(360,20,35,30);
        p1.add(phone);

        // Setting Image for three dot option
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);

        JLabel morevert  = new JLabel(i15);
        morevert.setBounds(420,20,10,25);
        p1.add(morevert);

        // Setting name of the person in front of photo
        JLabel name = new JLabel("Gaitonde");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.white);
        name.setFont(new Font("Arial",Font.BOLD,18));
        p1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.white);
        status.setFont(new Font("Arial",Font.BOLD,14));
        p1.add(status);


        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);

        text = new JTextField(); // for writing text
        text.setBounds(5,655,310,40);
        text.setFont(new Font("Arial",Font.PLAIN,16));// By default, the size of the font is 12
        f.add(text);

        // Adding send Button
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.white);
        send.addActionListener(this);// performing action when we click on send function
        send.setFont(new Font("Arial",Font.PLAIN,16));
        f.add(send);


        f.setSize(450,700);
        f.setLocation(200,50); // used to set the location of the frame , like where we want to open it
        f.setUndecorated(true); // to remove to top bar
        f.getContentPane().setBackground(Color.white);

        f.setVisible(true); // this statement should be last , as we want to show all changes to user


    }
    @Override
    public void actionPerformed(ActionEvent ae){// when we click on arrow icon in 3.png then it should close
        // the page
        try {
            String out = text.getText(); // return text in the form of String
            // we are adding out to new panel, because panel does not take String as
            // argument , so now we will pass p2 panel in the right panel
            JPanel p2 = formatLabel(out); // calling format label because we want to add messages in a box

            a1.setLayout(new BorderLayout()); // Border layout places element top, bottom, left and right

            JPanel right = new JPanel(new BorderLayout());// Setting panel on right side
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));// used to provide height to messages box

            a1.add(vertical, BorderLayout.PAGE_START);

            dos.writeUTF(out);

            text.setText("");// when you click on send button , text field will get empty

            // using function , so that reload can take place
            f.repaint(); // calling frame
            f.validate();
            f.invalidate();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");

        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102)); // these given parameters are for green color
        output.setOpaque(true); // without this function color change will not be visible
        output.setBorder(new EmptyBorder(15,15,15,50)); // for the padding

        panel.add(output);

        Calendar cal = Calendar.getInstance();// setting time with the message sent
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }
    public static void main(String[] args) {
        new Server();
        try{
            ServerSocket skt = new ServerSocket(6001);
            // accepting message from client infinitely, that's why while loop used
            while(true){
                Socket s = skt.accept();
                DataInputStream dis  = new DataInputStream(s.getInputStream());// receiving messages
                dos = new DataOutputStream(s.getOutputStream()); // sending messages

                while(true){
                    String msg = dis.readUTF();// reading messages using readUTF
                    JPanel panel = formatLabel(msg); // to display message on frame sent by client

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);// showing received messages on left
                    vertical.add(left);
                    f.validate();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
