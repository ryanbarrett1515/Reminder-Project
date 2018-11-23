import java.awt.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Reminder {

    private static int count;
    private static Semaphore countSem = new Semaphore(1);
    private static List<Reminder> reminderList = new ArrayList<>();
    private Thread thread;


    public Reminder(String message, Time time) {
        thread = new Thread(() -> {
            try {
                countSem.acquire();
                count++;
                countSem.release();
                Thread.sleep(time.getTime() - Time.valueOf(LocalTime.now()).getTime());
                SystemTray tray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                TrayIcon icon = new TrayIcon(image);
                tray.add(icon);
                icon.displayMessage("Reminder", message, TrayIcon.MessageType.WARNING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void cancel() {
        thread.interrupt();
    }

    public static int getCount() {
        return count;
    }

    public static List<Reminder> getReminderList() {
        return reminderList;
    }

    public static void setReminderList(List<Reminder> reminderList) {
        Reminder.reminderList = reminderList;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public static void joinAllThreads() {
        for (Reminder rem : reminderList) {
            try {
                synchronized (rem.getThread()) {
                    rem.getThread().wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        Reminder reminder = new Reminder("Testing", Time.valueOf(LocalTime.now()));
        getReminderList().add(reminder);
        joinAllThreads();
    }
}