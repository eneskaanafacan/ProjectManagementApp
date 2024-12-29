package entity;

import javax.swing.*;
import java.awt.*;

public class TaskStatusUpdateDialog extends JDialog {
    private JComboBox<Task.Status> statusComboBox;
    private boolean isConfirmed = false;

    public TaskStatusUpdateDialog(Frame owner, Task.Status currentStatus) {
        super(owner, "Durum Güncelle", true);

        setLayout(new BorderLayout(10, 10));
        setSize(300, 150);
        setLocationRelativeTo(owner);

        // ComboBox'ı oluştur
        statusComboBox = new JComboBox<>(Task.Status.values());
        statusComboBox.setSelectedItem(currentStatus);

        // Butonlar için panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton confirmButton = new JButton("Güncelle");
        JButton cancelButton = new JButton("İptal");

        confirmButton.addActionListener(e -> {
            isConfirmed = true;
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // Bileşenleri ekle
        add(new JLabel("Yeni Durum:", SwingConstants.CENTER), BorderLayout.NORTH);
        add(statusComboBox, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public Task.Status getSelectedStatus() {
        return (Task.Status) statusComboBox.getSelectedItem();
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
