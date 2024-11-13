//package game;
//
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class LargeSlotMachine extends SlotMachine {
//
//    public LargeSlotMachine(int numberOfSlots, int x, int y) {
//        super(numberOfSlots);
//        setLocation(x, y);
//        setPreferredSize(new Dimension(400, 400));
//        setBackground(Color.RED);
//
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                spin();
//                repaint();
//            }
//        });
//    }
//}