import java.sql.*;
import java.util.ArrayList;

public class NotesDAO {

    public boolean register(String username, String password) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(username, password) VALUES(?,?)"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public int login(String username, String password) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT user_id FROM users WHERE username=? AND password=?"
            );
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("user_id");
            else
                return -1;

        } catch (Exception e) {
            return -1;
        }
    }

    public boolean addNote(int userId, String title, String content) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO notes(user_id, title, content) VALUES(?,?,?)"
            );
            ps.setInt(1, userId);
            ps.setString(2, title);
            ps.setString(3, content);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<String> getNotes(int userId) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT title, content FROM notes WHERE user_id=?"
            );
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add("Title: " + rs.getString("title")
                        + "\nContent: " + rs.getString("content"));
            }
        } catch (Exception e) {}
        return list;
    }
}
