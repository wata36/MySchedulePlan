

# スケジュール管理アプリ【個人のタスク効率化】
🚀 URL: http://160.251.170.210:8080/Schedule/login.jsp

Java/JSPを使って、ログイン機能とタスクのCRUD操作（作成・読み込み・削除）を学習するために制作したアプリケーションです。

---

## ⭐️ アプリの機能と現在の状況

### 🚀目的

個人の予定をアカウントごとに管理し、一日のスケジュールを効率的に可視化・計画するためのアプリケーション。ユーザーが漏れなく、計画的に行動できるように支援します。


### ✍️機能

アカウントごとに日付タイトルの一覧を作り、予定の詳細(時間,場所,地図)を１日のスケジュールを組むことができるアプリです。
1日の中で時間ごとのスケジュールを組み立て、効率的な予定管理が可能です。

| 機能 | 状況 | 詳細 |
| :--- | :--- | :--- |
| **ログイン機能** | **完了** | MySQLのusers テーブルと連携したユーザー認証ロジックを実装済みです。パスワードのハッシュ化も対応済み。 |
| **新規登録** | **完了** | MySQLのusers テーブルと連携したユーザー登録ロジックを実装済みです。ユーザーIDの重複を防ぐようにしています。 |
| **予定、予定詳細追加** | **完了** |予定タイトル、日付、詳細（時間・場所・地図）をDBに格納するロジックを実装。 |
| **予定、予定詳細一覧** | **完了** | データ取得・表示ロジックは実装です。 |
| **予定詳細更新/削除** | **完了** | データベースの更新/削除ロジックは実装です。更新、JSにて実装。地図のみ変更の反映に至ってません。引き続き実装中。 |

＊三層構造：DAO (DB)、Service (ビジネスロジック)、Servlet (制御)、JSP (画面) の役割分担で作成しています。

### 📷 設計スクリーンショット

![ER図](./screenshot/ER図.png)
![画面設計図](./screenshot/画面遷移図.png)


### 🚶 今後の展望

1. **正しい画面遷移と処理の完成**：
   * 予定の追加や削除の**Servlet処理**を修正し、DBへの情報格納処理を完全に動作させます。
2. **機能の追加（より便利なものに）**：
   * ユーザー,予定の**編集**機能を実装します。
   * 予定の詳細のタスク**完了**機能実装します。
   * ユーザーの登録**確認**画面への遷移。   
3. **フレームワークへの移行（実務志向）**：
   * 次のステップとして、**Spring Bootフレームワーク**での実装を目指します。実務で求められる**保守性・拡張性の高い開発**を早期に実現するため、学習を継続します。 
---

## ⭐️ 技術スタック

* **言語**: Java (2025)
* **Webフレームワーク**: Servlet / JSP
* **サーバーサイド**: Java / Servlet / JSP
* **フロントエンド(UI)**: HTML / CSS / JavaScript / JSP
* **データベース**: MySQL
* **Webサーバー**: Apache Tomcat
* **開発環境**: Eclipse (macOS / Intel Core i5)

---

## ⭐️ 操作手順(スクリーンショット)
<div align="center">
  <img src="./screenshot/ログイン画面.png" alt="ログイン画面" width="300px">
  <img src="./screenshot/メイン画面.png" alt="メイン画面" width="300px">
  <img src="./screenshot/予定詳細画面.png" alt="予定詳細" width="300px">
</div>


### 🚀トラブルシューティング：VPS接続時のDBの接続障害と解決
1. **ネットワーク層の障害**：
   * **MySQLがローカル接続以外を制限していた。**
   * 設定ファイルに```bind-address = 0.0.0.0```を追記し、全IPからの接続を許可。
2. **権限層の障害**：
   * **skip-grant-tables オプションが設定ファイルに残留し、権限設定変更が拒否された (ERROR 1290)。**
   * grep コマンドで原因ファイル (collation.cnf) を特定し、```skip-grant-tables``` を強制削除。```skip-grant-tables```が発生したのはrootからセキュリティのため名前を変更してしまったためrootでのパスワードがわからなくなったため設定していたため。
3. **接続認証層の障害**：
    * **アプリコード不変のため、localhost 接続がサーバー側で拒否される問題を回避できなかった。**
    * DBユーザーのホスト名を ```schedule_user@'localhost'``` から ```schedule_user@'%'```(ワイルドカード) へ変更し、Tomcatの接続制約をサーバー設定で乗り越えた。
4. **文字区別の障害**：    
   * **Linux上のMySQLはデフォルトでテーブル名の大文字・小文字を区別する。Tomcatアプリが想定するテーブル名と、DBに実際に作成されたテーブル名で、大文字・小文字の表記が微妙に異なっていた可能性がある。**
   * テーブルをすべて大文字にしたが解決ができず、MySQL設定ファイルに ```lower_case_table_names = 1 ```を追記した。これにより、MySQLはテーブル名の大文字・小文字を区別しないように解決した。
5. **プロセスからの学び**：    
   * データベースのテーブル構造をクリーンに保つことの重要性を再認識した。また、今回の複合的なエラーを通じて、単一のエラー解決に集中するのではなく、先の問題に対しても意識し解決することの重要さを習得した。

 ###  データベース設定  
```sql
-- データベース作成
CREATE DATABASE schedule_app_db;
-- usersテーブル作成
CREATE TABLE schedule_app_db.users (
user_id INT PRIMARY KEY AUTO_INCREMENT,
user_login VARCHAR(50) UNIQUE NOT NULL,
user_name VARCHAR(255) UNIQUE NOT NULL,
password VARCHAR(256) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- scheduleテーブル作成
CREATE TABLE schedule_app_db.schedule (
schedule_id INT PRIMARY KEY AUTO_INCREMENT,
user_id INT NOT NULL,
title VARCHAR(255) NOT NULL,
date DATE NOT NULL,
FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- schedule_detailテーブル作成
CREATE TABLE schedule_app_db.schedule_detail (
detail_id INT PRIMARY KEY AUTO_INCREMENT,
schedule_id INT NOT NULL,
time TIME NOT NULL,
place VARCHAR(255) NOT NULL,
detail VARCHAR(1000) NOT NULL,
map VARCHAR(1000) NOT NULL,  
FOREIGN KEY (schedule_id) REFERENCES schedule(schedule_id)
);
