//class = "delete" の要素を取得する（querySelectorAll)
//そのボタンがsubmitされたタイミングでalertを表示する
//alertでOKが押されたらsubmitする


//削除アラート
document.addEventListener('DOMContentLoaded', function() {
    
    // フォームを取得
    const deleteForms = document.querySelectorAll('form[action="ScheduleDleateServlet"]');

    deleteForms.forEach(form => {
        
        // async(ユーザーがボタンを押すという非同期的な処理の完了を待つため）追加
		// 削除押したときに実行される。
        form.addEventListener('submit', async function(event) {
            
            event.preventDefault(); // デフォルト動作を停止
            
            const result = await Swal.fire({
                title: '削除しますか？',
                icon: 'warning', 
                showCancelButton: true, 
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'はい', 
                cancelButtonText: 'いいえ' 
            });

            // OKボタンが押されたかどうかをチェック
            if (result.isConfirmed) {
                
                // OKが押されたら、フォームを送信
                this.submit(); 
                
            } else {
       
            }
        });
    });
});

console.log("script.js loaded");

//登録アラート
document.addEventListener('DOMContentLoaded', function() {
  if (window.registrationComplete === true || window.registrationComplete === 'true') {
    Swal.fire({
      title: '登録完了',
      text: 'ユーザー登録が完了しました！',
      icon: 'success',
      confirmButtonText: 'OK'
    });
  }
});
