package com.dynsol.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dynsol.model.Message;

public class SQliteDatabase {
	Connection connection = null;

	public SQliteDatabase() {
		//setDatabase();
	}

	public String getAllData() {
		return "test";
	}

	SimpleDateFormat df = new SimpleDateFormat(
			"E yyyy.MM.dd 'at' hh:mm:ss a zzz");

	private void setDatabase() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("drop table if exists message");
			statement
					.executeUpdate("create table IF NOT EXISTS message  (id integer PRIMARY KEY AUTOINCREMENT, name string UNIQUE,author string,created string)");
			statement
					.executeUpdate("insert into message(name,author,created) values('Walter','Walter','"
							+ df.format(new Date()) + "')");
			statement
					.executeUpdate("insert into message(name,author,created) values('Mulwa','Walter','"
							+ df.format(new Date()) + "')");
			statement
					.executeUpdate("insert into message(name,author,created) values('Emmanuel','Walter','"
							+ df.format(new Date()) + "')");

		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}

	}

	public ArrayList<Message> messageResults = new ArrayList<Message>();

	public List<Message> getAllMessages() {
		try {
			Statement statement;
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery("select * from message");
			while (rs.next()) {
				messageResults.add(new Message(rs.getInt("id"), rs
						.getString("name"), rs.getString("author"), rs
						.getString("created")));

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {

			}
		}
		return messageResults;
	}

	public Message insertMessage(Message msg) {
		String name = msg.getName();
		String author = msg.getAuthor();
		String date = msg.getDATE_CREATED();
		long id;
		// SELECT * FROM tablename ORDER BY column DESC LIMIT 1;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("insert into message(name,author,created) "
					+ "values('" + name + "','" + author + "','" + date + "')");
			ResultSet rs = statement
					.executeQuery("SELECT id FROM message ORDER BY id DESC LIMIT 1");
			while (rs.next()) {
				id = rs.getInt("id");
				msg.setId(id);
			}
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}

		return msg;
	}
}
