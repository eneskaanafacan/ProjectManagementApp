package view.DashBoardPage;

import Business.DashBoardController;
import Business.EmployeeController;
import Business.ProjectController;
import core.Helper;
import entity.*;
import view.LogInPage.LogInView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;

public class DashBoardView extends JFrame {
    private ProjectController projectController;
    private EmployeeController employeeController;
    private DashBoardController dashBoardController;
    private User user;
    private JPanel container;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JTabbedPane tab_menu;
    private JPanel pnl_customer;
    private JPanel pnl_customerFilter;
    private JScrollPane scrl_projects;
    private JTable tbl_project;
    private JTextField fld_projectName;
    private JButton btn_addProject;
    private JButton btn_searchProject;
    private JLabel lbl_ProjectName;
    private JPanel pnl_employee;
    private JTable tbl_employee;
    private JPanel pnl_employee_filter;
    private JTextField fld_f_employee_name;
    private JButton btn_employeeSearch;
    private JButton btn_employeeAdd;
    private JLabel lbl_f_employee_name;
    private DefaultTableModel tableModelProjects = new DefaultTableModel();
    private DefaultTableModel tableModelEmployees = new DefaultTableModel();
    private JPopupMenu popupMenu_employee = new JPopupMenu();
    private JPopupMenu popupMenu_project = new JPopupMenu();

    public DashBoardView(User user) {
        this.user = user;
        this.employeeController = new EmployeeController();
        this.projectController = new ProjectController();
        this.dashBoardController = new DashBoardController();

        if (user == null) {
            Helper.showMessage("error");
        }

        this.add(container);
        this.setTitle("Proje Yönetim Sistemi");
        this.setSize(1000, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.lbl_welcome.setText("Hoşgeldin: " + this.user.getUsername());

        this.btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LogInView logInView = new LogInView();
            }
        });

        // Project tab
        loadProjectTable(null);
        loadProjectPopupMenu();
        loadProjectButtonEvent();

        // Employee tab
        loadEmployeeTable(null);
        loadEmployeePopupMenu();
        loadEmployeeButtonEvent();
    }

    /////// Employee methods
    private void loadEmployeeTable(ArrayList<Employee> employees) {
        Object[] columnProject = {"Çalışan ID", "İsim", "Soyisim", "Pozisyon", "E-Mail"};

        if (employees == null) {
            employees = this.employeeController.findAll();
        }

        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_employee.getModel();
        clearModel.setRowCount(0);

        this.tableModelEmployees.setColumnIdentifiers(columnProject);
        for (Employee employee : employees) {
            Object[] rowObject = {
                    employee.getEmployeeID(),
                    employee.getName(),
                    employee.getSurname(),
                    employee.getPosition(),
                    employee.getEmail()
            };
            this.tableModelEmployees.addRow(rowObject);
        }

        this.tbl_employee.setModel(tableModelEmployees);
        this.tbl_employee.getTableHeader().setReorderingAllowed(false);
        this.tbl_employee.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_employee.setEnabled(false);
    }

    private void loadEmployeePopupMenu() {
        this.tbl_employee.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            private void showPopup(MouseEvent e) {
                int selectedRow = tbl_employee.rowAtPoint(e.getPoint());
                if (selectedRow != -1) {
                    tbl_employee.setRowSelectionInterval(selectedRow, selectedRow);
                    popupMenu_employee.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        this.popupMenu_employee.add("Güncelle").addActionListener(e -> {
            int selectedId = (int) tbl_employee.getValueAt(tbl_employee.getSelectedRow(), 0);
            Employee editedEmployee = this.employeeController.getById(selectedId);
            EmployeeView employeeView = new EmployeeView(editedEmployee);
            employeeView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadEmployeeTable(null);
                }
            });
        });

        this.popupMenu_employee.add("Sil").addActionListener(e -> {
            int selectedId = (int) tbl_employee.getValueAt(tbl_employee.getSelectedRow(), 0);
            if (Helper.isConfirm("sure?")) {
                if (this.employeeController.delete(selectedId)) {
                    Helper.showMessage("done");
                    loadEmployeeTable(null);
                } else {
                    Helper.showMessage("error");
                }
            }
        });

        this.tbl_employee.setComponentPopupMenu(this.popupMenu_employee);
    }

    private void loadEmployeeButtonEvent() {
        this.btn_employeeAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeView employeeView = new EmployeeView(new Employee());
                employeeView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadEmployeeTable(null);
                    }
                });
            }
        });

        this.btn_employeeSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeName = fld_f_employee_name.getText().trim();
                if (!employeeName.isEmpty()) {
                    ArrayList<Employee> filteredEmployees = employeeController.findByName(employeeName);
                    loadEmployeeTable(filteredEmployees);
                } else {
                    Helper.showMessage("Lütfen bir çalışan ismi girin.");
                }
            }
        });
    }

    /////// Project methods
    private void loadProjectTable(ArrayList<Project> projects) {
        Object[] columnProject = {"ID", "Çalışan ID", "Proje Adı", "Başlangıç Tarihi", "Bitiş Tarihi", "Gecikme Günü"};

        if (projects == null) {
            projects = this.projectController.findAll();
        }

        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_project.getModel();
        clearModel.setRowCount(0);

        this.tableModelProjects.setColumnIdentifiers(columnProject);
        for (Project project : projects) {
            Object[] rowObject = {
                    project.getProjectID(),
                    project.getEmployeeID(),
                    project.getName(),
                    project.getStartDate(),
                    project.getEndDate(),
                    project.getDelayDays()
            };
            this.tableModelProjects.addRow(rowObject);
        }

        this.tbl_project.setModel(tableModelProjects);
        this.tbl_project.getTableHeader().setReorderingAllowed(false);
        this.tbl_project.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_project.setEnabled(false);
    }

    private void loadProjectPopupMenu() {
        this.tbl_project.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            private void showPopup(MouseEvent e) {
                int selectedRow = tbl_project.rowAtPoint(e.getPoint());
                if (selectedRow != -1) {
                    tbl_project.setRowSelectionInterval(selectedRow, selectedRow);
                    popupMenu_project.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });


        this.popupMenu_project.add("Görevler").addActionListener(e -> {
            int selectedProjectId = (int) tbl_project.getValueAt(tbl_project.getSelectedRow(), 0);
            Project projectWithTasks = this.projectController.getById(selectedProjectId);

            JFrame taskFrame = new JFrame("Görevler");
            taskFrame.setSize(1605, 740);
            taskFrame.setLocationRelativeTo(this);
            taskFrame.setLayout(null);

            JPanel taskPanel = new JPanel();
            taskPanel.setBounds(0, 0, 1605, 740);
            taskPanel.setLayout(null);
            taskPanel.setBackground(new Color(211, 211, 211));

            // Form elemanları
            JLabel lbl_employeeId = new JLabel("Çalışan ID");
            lbl_employeeId.setBounds(10, 20, 80, 25);
            taskPanel.add(lbl_employeeId);

            JTextField txt_employeeId = new JTextField();
            txt_employeeId.setBounds(100, 20, 150, 25);
            taskPanel.add(txt_employeeId);

            JLabel lbl_taskId = new JLabel("Görev ID");
            lbl_taskId.setBounds(270, 20, 80, 25);
            taskPanel.add(lbl_taskId);

            JTextField txt_taskId = new JTextField();
            txt_taskId.setBounds(360, 20, 150, 25);
            taskPanel.add(txt_taskId);

            JLabel lbl_taskName = new JLabel("Görev İsmi");
            lbl_taskName.setBounds(530, 20, 80, 25);
            taskPanel.add(lbl_taskName);

            JTextField txt_taskName = new JTextField();
            txt_taskName.setBounds(620, 20, 150, 25);
            taskPanel.add(txt_taskName);

            JLabel lbl_startDate = new JLabel("Başlangıç Tarihi");
            lbl_startDate.setBounds(790, 20, 100, 25);
            taskPanel.add(lbl_startDate);

            JTextField txt_startDate = new JTextField();
            txt_startDate.setBounds(900, 20, 150, 25);
            taskPanel.add(txt_startDate);

            JLabel lbl_endDate = new JLabel("Bitiş Tarihi");
            lbl_endDate.setBounds(1070, 20, 80, 25);
            taskPanel.add(lbl_endDate);

            JTextField txt_endDate = new JTextField();
            txt_endDate.setBounds(1160, 20, 150, 25);
            taskPanel.add(txt_endDate);

            JLabel lbl_workDays = new JLabel("İş Günü");
            lbl_workDays.setBounds(10, 60, 80, 25);
            taskPanel.add(lbl_workDays);

            JTextField txt_workDays = new JTextField();
            txt_workDays.setBounds(100, 60, 150, 25);
            taskPanel.add(txt_workDays);

            JLabel lbl_status = new JLabel("Durum");
            lbl_status.setBounds(270, 60, 80, 25);
            taskPanel.add(lbl_status);

            JComboBox<Task.Status> cmb_status = new JComboBox<>(Task.Status.values());
            cmb_status.setBounds(360, 60, 150, 25);
            taskPanel.add(cmb_status);

            JButton btn_addTask = new JButton("Ekle");
            btn_addTask.setBounds(530, 60, 100, 25);
            taskPanel.add(btn_addTask);

            // Görev tablosu
            String[] columnNames = {"Görev ID", "Çalışan ID", "Görev Adı", "Başlangıç", "Bitiş", "İş Günü", "Durum"};
            DefaultTableModel taskTableModel = new DefaultTableModel(columnNames, 0);
            JTable taskTable = new JTable(taskTableModel);
            JScrollPane scrollPane = new JScrollPane(taskTable);
            scrollPane.setBounds(10, 100, 1580, 600);
            taskPanel.add(scrollPane);

            JPopupMenu taskPopupMenu = new JPopupMenu();
            taskTable.setComponentPopupMenu(taskPopupMenu);

            JMenuItem deleteMenuItem = new JMenuItem("Görevi Sil");
            JMenuItem updateStatusMenuItem = new JMenuItem("Durumu Güncelle");
            taskPopupMenu.add(deleteMenuItem);
            taskPopupMenu.add(updateStatusMenuItem);



            // Görevleri yükle
            loadTasks(taskTableModel, selectedProjectId);

            taskTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        showTaskPopup(e);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        showTaskPopup(e);
                    }
                }

                private void showTaskPopup(MouseEvent e) {
                    int row = taskTable.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < taskTable.getRowCount()) {
                        taskTable.setRowSelectionInterval(row, row);
                        taskPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });



            deleteMenuItem.addActionListener(deleteEvent -> {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    int taskId = (Integer) taskTable.getValueAt(selectedRow, 0);

                    if (Helper.isConfirm("Görevi silmek istediğinizden emin misiniz?")) {
                        if (dashBoardController.deleteTask(taskId)) {
                            Helper.showMessage("Görev başarıyla silindi.");
                            loadTasks(taskTableModel, selectedProjectId);
                        } else {
                            Helper.showMessage("Görev silinirken bir hata oluştu.");
                        }
                    }
                } else {
                    Helper.showMessage("Lütfen silinecek görevi seçin.");
                }
            });

            updateStatusMenuItem.addActionListener(updateEvent -> {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    int taskId = (Integer) taskTable.getValueAt(selectedRow, 0);
                    String currentStatusStr = (String) taskTable.getValueAt(selectedRow, 6);
                    Task.Status currentStatus = Task.Status.valueOf(currentStatusStr.toUpperCase().replace(" ", "_"));

                    TaskStatusUpdateDialog dialog = new TaskStatusUpdateDialog(taskFrame, currentStatus);
                    dialog.setVisible(true);

                    if (dialog.isConfirmed()) {
                        Task.Status newStatus = dialog.getSelectedStatus();
                        if (dashBoardController.updateTaskStatus(taskId, newStatus)) {
                            Helper.showMessage("Görev durumu başarıyla güncellendi.");
                            loadTasks(taskTableModel, selectedProjectId);
                        } else {
                            Helper.showMessage("Görev durumu güncellenirken bir hata oluştu.");
                        }
                    }
                } else {
                    Helper.showMessage("Lütfen durumu güncellenecek görevi seçin.");
                }
            });

            btn_addTask.addActionListener(e1 -> {
                try {
                    Integer taskId = Integer.parseInt(txt_taskId.getText());
                    Integer employeeId = Integer.parseInt(txt_employeeId.getText());
                    String taskName = txt_taskName.getText();
                    Date startDate = Date.valueOf(txt_startDate.getText()); // Format: YYYY-MM-DD
                    Date endDate = Date.valueOf(txt_endDate.getText()); // Format: YYYY-MM-DD
                    Double workDays = Double.parseDouble(txt_workDays.getText());
                    Task.Status status = (Task.Status) cmb_status.getSelectedItem();

                    if (taskName.isEmpty()) {
                        Helper.showMessage("Görev adı boş olamaz!");
                        return;
                    }

                    // Çalışanın daha önce herhangi bir projede görevi olup olmadığını kontrol et
                    if (dashBoardController.isEmployeeAssignedToAnyTask(employeeId)) {
                        Helper.showMessage("Bu çalışan zaten başka bir projede görevlendirilmiş. Lütfen başka bir çalışan seçin.");
                        return;
                    }

                    Task newTask = new Task(
                            taskId,
                            selectedProjectId,
                            employeeId,
                            taskName,
                            startDate,
                            endDate,
                            workDays,
                            status
                    );

                    if (dashBoardController.addTask(newTask)) {
                        Helper.showMessage("Görev başarıyla eklendi.");
                        loadTasks(taskTableModel, selectedProjectId);
                        clearTaskFields(txt_taskId, txt_employeeId, txt_taskName,
                                txt_startDate, txt_endDate, txt_workDays);
                    } else {
                        Helper.showMessage("Görev eklenirken bir hata oluştu.");
                    }
                } catch (IllegalArgumentException ex) {
                    Helper.showMessage("Lütfen tüm alanları doğru formatta doldurun:\n" +
                            "- Tarihler YYYY-MM-DD formatında olmalı\n" +
                            "- ID'ler ve iş günü sayısal olmalı");
                }
            });


            taskFrame.add(taskPanel);
            taskFrame.setVisible(true);
        });


        this.popupMenu_project.add("Sil").addActionListener(e -> {
            int selectedId = (int) tbl_project.getValueAt(tbl_project.getSelectedRow(), 0);
            if (Helper.isConfirm("sure?")) {
                if (this.projectController.delete(selectedId)) {
                    Helper.showMessage("done");
                    loadProjectTable(null);
                } else {
                    Helper.showMessage("error");
                }
            }
        });
        this.tbl_project.setComponentPopupMenu(this.popupMenu_project);
    }

    private void loadTasks(DefaultTableModel model, int projectId) {
        model.setRowCount(0);
        ArrayList<Task> tasks = dashBoardController.getTasksByProjectId(projectId);

        for (Task task : tasks) {
            Object[] row = {
                    task.getTaskID(),
                    task.getEmployeeID(),
                    task.getName(),
                    task.getStartDate(),
                    task.getEndDate(),
                    task.getWorkDays(),
                    task.getStatus().getStatus()
            };
            model.addRow(row);
        }
    }


    private void clearTaskFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private void loadProjectButtonEvent() {
        this.btn_addProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProjectView projectView = new ProjectView(new Project());
                projectView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadProjectTable(null);
                    }
                });
            }
        });

        this.btn_searchProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectName = fld_projectName.getText().trim();
                if (!projectName.isEmpty()) {
                    ArrayList<Project> filteredProjects = projectController.findByName(projectName);
                    loadProjectTable(filteredProjects);
                } else {
                    Helper.showMessage("Lütfen proje ismi girin.");
                }
            }
        });
    }
}