package DAOs;

import java.sql.*;
import java.util.*;

import classes.*;
import connectionFactory.ConnectionDb;

public class TaskDAO {

    public static List<Task> getTimeLine() {
      List<Task> tasks = new ArrayList<Task>();

      ResultSet rset = null;
      Connection conn = null;
      PreparedStatement pstm = null;

      try {
        conn = ConnectionDb.createConnection();
        pstm = conn.prepareStatement("SELECT * FROM task ORDER BY final_date");
        rset = pstm.executeQuery();

        while (rset.next()) {
          Task task = new Task(
            rset.getInt("task_id"),
            rset.getString("title"),
            rset.getString("description"),
            rset.getTimestamp("final_date").toLocalDateTime(),
            rset.getInt("complete"),
            rset.getInt("tag_id")
          );
          tasks.add(task);
        }

      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (rset != null) rset.close();
          if (pstm != null) pstm.close();
          if (conn != null) conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      return tasks;
    }

    public static void createTask(Task task) {
      String keys = "(title, description, final_date, complete, tag_id)";
      String sql = "INSERT INTO task " + keys + " VALUES ('"
        + task.getTitle() + "', '" 
        + task.getDescription() + "', '" 
        + task.getFinalDate() + "', '"
        + task.getComplete() + "', '"
        + task.getTagId() + "');";

      Connection conn = null;
      PreparedStatement pstm = null;

      try {
        conn = ConnectionDb.createConnection();
        pstm = conn.prepareStatement(sql);
        pstm.execute();
      } catch (Exception e) {
          e.printStackTrace();
      } finally {
        try {
          if (pstm != null) pstm.close();
          if (conn != null) conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    public static void updateTask(Task task) {
        String sql = "UPDATE task SET "
            + "title = '" + task.getTitle() + "', "
            + "description = '" + task.getDescription() + "', " 
            + "final_date = '" + task.getFinalDate() + "', " 
            + "complete = " + task.getComplete() + ", "
            + "tag_id = " + task.getTagId() + " " 
            + "WHERE task_id = " + task.getId();
    
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = ConnectionDb.createConnection();
            pstm = conn.prepareStatement(sql);
            pstm.addBatch("SET FOREIGN_KEY_CHECKS=0");
            pstm.executeBatch();
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void deleteTask(int task_id) {
        String sql = "DELETE FROM task WHERE task_id = " + task_id + ";";
        Connection conn = null;
        PreparedStatement pstm = null;
        

        try {
            conn = ConnectionDb.createConnection();
            pstm = conn.prepareStatement(sql);
            pstm.addBatch("SET FOREIGN_KEY_CHECKS=0");
            pstm.executeBatch();
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Task> getTaskByTag(int tag_id) {
      List<Task> tasks = new ArrayList<Task>();

      ResultSet rset = null;
      Connection conn = null;
      PreparedStatement pstm = null;

      try {
        conn = ConnectionDb.createConnection();
        pstm = conn.prepareStatement("SELECT * FROM task WHERE tag_id = " + tag_id);
        rset = pstm.executeQuery();

        while (rset.next()) {
          Task task = new Task(
            rset.getInt("task_id"),
            rset.getString("title"),
            rset.getString("description"),
            rset.getTimestamp("final_date").toLocalDateTime(),
            rset.getInt("complete"),
            rset.getInt("tag_id")
          );
          tasks.add(task);
        }

      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (rset != null) rset.close();
          if (pstm != null) pstm.close();
          if (conn != null) conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      return tasks;
    }
}