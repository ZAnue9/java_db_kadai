package text.kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {

	public static void main(String[] args) {

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		String[][] postsList = {
				
			    { "1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13" },
				{ "1002", "2023-02-08", "お疲れ様です！", "12" },
				{ "1003", "2023-02-09", "今日も頑張ります！", "18" },
				{ "1001", "2023-02-09", "無理は禁物ですよ！", "17" },
				{ "1002", "2023-02-10", "明日から連休ですね！", "20" }
		};

		try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "mY472558"
            );

			System.out.println("データベース接続成功:"+ con);

			// SQLクエリを準備
			String sql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
			statement = con.prepareStatement(sql);
			


			int rowCnt = 0;
			System.out.println("レコード追加を実行します");
			for (String[]post:postsList) {
				statement.setString(1, post[0]);
				statement.setDate(2, Date.valueOf(post[1]));
				statement.setString(3, post[2]);
				statement.setString(4, post[3]);
				
				statement.executeUpdate();
				rowCnt++;
			}
			System.out.println( rowCnt + "件のレコードが追加されました");
			
			String selectUser = "SELECT posted_at, post_content, likes FROM posts where user_id=1002;";
			statement = con.prepareStatement(selectUser);
			
			System.out.println("ユーザーIDが1002のレコードを検索しました");
						
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
		      Date postedAt = resultSet.getDate("posted_at");
		      String posetContent = resultSet.getString("post_content");
		      int likes = resultSet.getInt("likes");
		      System.out.println(
		    		  resultSet.getRow() + "件目：投稿日時=" + postedAt + "／投稿内容" + posetContent + "／いいね数" + likes);
			}
			
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			// 使用したオブジェクトを解放
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {
				}
			}
		}
	}
}
