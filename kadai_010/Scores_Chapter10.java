package text.kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {
	public static void main(String[] args) {

		Connection con = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			// データベースに接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"mY472558");

			System.out.println("データベース接続成功: " + con);

			// トランザクションを開始
			con.setAutoCommit(false);

			// ステートメント作成
			statement = con.createStatement();

			// UPDATE 文を実行
			String sqlUpdate = "UPDATE scores SET score_math = 95, score_english = 80 WHERE id = 5;";
			System.out.println("レコード更新を実行します");
			int rowCnt = statement.executeUpdate(sqlUpdate);
			System.out.println(rowCnt + "件のレコードが更新されました");

			// SELECT 文を実行
			String sqlSelect = "SELECT id, name, score_math, score_english " +
					"FROM scores " +
					"ORDER BY score_math DESC, score_english DESC;";
			resultSet = statement.executeQuery(sqlSelect);

			System.out.println("数学・英語の点数が高い順に並べ替えました:");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int scoreMath = resultSet.getInt("score_math");
				int scoreEnglish = resultSet.getInt("score_english");
				System.out.printf("生徒ID=%d／氏名=%s／数学=%d／英語=%d%n",
						id, name, scoreMath, scoreEnglish);
			}

		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
			if (con != null) {
			}
		} finally {
			// 使用したオブジェクトを解放
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException ignore) {
				}
			}
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