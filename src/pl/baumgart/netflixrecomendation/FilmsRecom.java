package pl.baumgart.netflixrecomendation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by Natalia on 26.01.14.
 */
public class FilmsRecom {
    private JPanel panel1;
    private JLabel title1;
    private JLabel title2;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton addButton;
    private JLabel labelFilm;
    private JLabel labelRate;
    private JButton showRecommendationsButton;
    private JList list1;
    private JTextField jtf;
    public RecomendationUtility recomendationUtility;

    public FilmsRecom() {
        JFrame frame = new JFrame("Films");
        frame.setContentPane(this.panel1);
        //panel1.setPreferredSize(new Dimension(500,600));
        //panel1.add(comboBox);
        //panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
        frame.pack();
        frame.setResizable(false);
        //frame.setBounds(50, 50, 300, frame.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new FilmsRecom();
            }
        });
    }
    public Object[] getRates() {
        return new Object[] {
                "1", "2", "3", "4","5"
        };
    }
    private void createUIComponents() {
        final Vector<ScoredMovie> ratedMovies = new Vector<>();

        // to ponizej jakos nie bardzo chce dzialac i nie mam sily juz na to, wiec wpisalam wartosc 5 na sztywno w kreatorze
       // title1 = new JLabel();
       // title1.setText("Have no idea which film to watch?");
        //title2 = new JLabel();
        //title2.setText("Rate at least" + RecomendationUtility.NUMBER_OF_FEATURES_USED +" you've already seen and checkout the recommendations");
        recomendationUtility = new RecomendationUtility();

        comboBox2 = new JComboBox(getRates());
        comboBox1  = new JComboBox(recomendationUtility.getMovies().toArray());
        comboBox1.setSelectedIndex(-1);
        jtf = (JTextField)comboBox1.getEditor().getEditorComponent();
        jtf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                Object obj = e.getSource();
                if (obj == jtf) {
                    String key = jtf.getText();
                    comboBox1.removeAllItems();
                    comboBox1.setPopupVisible(false);
                    for (Movie m : recomendationUtility.getMovies()) {
                        if (((String)m.title.toLowerCase()).contains(key.toLowerCase())) {
                            comboBox1.addItem(m);

                        }
                    }
                    comboBox1.setPopupVisible(true);
                    comboBox1.revalidate();
                    jtf.setText(key);

                }
            }
        });
        comboBox1.setSelectedIndex(-1);
        list1=new JList<ScoredMovie>(ratedMovies);
        list1.addKeyListener( new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_DELETE)
                {

                    if(list1.getSelectedIndices().length > 0) {
                        int[] selectedIndices = list1.getSelectedIndices();
                        for (int i = selectedIndices.length-1; i >=0; i--) {
                            ratedMovies.removeElementAt(selectedIndices[i]);
                        }
                    }

                    list1.setListData(ratedMovies);
                    if(ratedMovies.size()<RecomendationUtility.NUMBER_OF_FEATURES_USED)
                        showRecommendationsButton.setEnabled(false);

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        addButton = new JButton();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(comboBox1.getSelectedItem()!=null)
                {
                    ratedMovies.add( new ScoredMovie((Movie)comboBox1.getSelectedItem(),Double.parseDouble((String) comboBox2.getSelectedItem())));
                    list1.setListData(ratedMovies);
                }
                if(ratedMovies.size()>=RecomendationUtility.NUMBER_OF_FEATURES_USED)
                    showRecommendationsButton.setEnabled(true);

            }

        });

//
        showRecommendationsButton = new JButton();
        showRecommendationsButton.setEnabled(false);
        showRecommendationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Vector<ScoredMovie> scoredMovies = recomendationUtility.recommend(ratedMovies);
                Vector<ScoredMovie> scoredtop10 = new Vector<>();
                Collections.sort(scoredMovies);
                Collections.reverse(scoredMovies);

                for (int i = 0; i < 10; i++) {
                    scoredtop10.add(scoredMovies.get(i));
                    System.out.println(scoredMovies.get(i).score + "\t" + scoredMovies.get(i).movie.title);
                }

                list1.setListData(scoredtop10);
                ratedMovies.clear();
            } 

        });


    }
}
