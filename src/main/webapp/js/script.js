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

///詳細変更
// 隠しフィールドhiddenの登録（regist)を変更ボタンを押したときだけ変更(mode)に切り替える

document.addEventListener('DOMContentLoaded', function() {
    // 画面上部のフォーム入力欄を取得（データを受け取る場所）
    const form = document.querySelector('.mainbox form');
    if (!form) return; // フォームがない場合は処理を終了
    
    // フォーム内の各入力フィールドを取得
    const timeInput = form.querySelector('input[name="time"]');
    const placeInput = form.querySelector('input[name="place"]');
    const detailInput = form.querySelector('input[name="detail"]');
    const mapInput = form.querySelector('input[name="map"]');
	const actionInput = form.querySelector('input[name="action"]'); 
	const detailIdInput = form.querySelector('input[name="detail_id"]'); 
	const submitButton = form.querySelector('input[type="submit"]');
	const detailForm = document.getElementById("detail-form");
    // スケジュールリストの中の「変更」ボタンを全て取得
    const editButtons = document.querySelectorAll('.planbox .ButtonGroup form[action="SchedulemodifyServlet"] .action-button');

    //  「変更」ボタンにクリックされたときの処理を設定
    editButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            
            // フォーム送信をキャンセルデータコピーのみを実行
            event.preventDefault(); 
            
            // クリックされたボタンの親の親要素 (.planbox) を見つける
            const planBox = this.closest('.planbox');

            // planbox 内の表示されているデータを取得
            const time = planBox.querySelector('.time').textContent.trim();
            const place = planBox.querySelector('.place').textContent.trim();
            const detail = planBox.querySelector('.detail').textContent.trim();
            const map = planBox.querySelector('.map').textContent.trim(); 
            
            // 変更対象の detail_id を隠しフィールドから取得
            const detailId = this.form.querySelector('input[name="detail_id"]').value;
			const scheduleId = document.querySelector('input[name="schedule_id"]').value;

            // 取得したデータを画面上部のフォーム入力欄にセット（コピー）
            timeInput.value = time;
            placeInput.value = place;
            detailInput.value = detail;
            mapInput.value = map;
            detailIdInput.value = detailId; 
			scheduleId.value = scheduleId; 


			// モードとボタンの表示を「更新」に切り替える
            if (actionInput) {
                actionInput.value = "update"; // hidden input の action を 'update' に変更
            }
            if (submitButton) {
                submitButton.value = "更新"; // 送信ボタンのテキストを「更新」に変更
            }
			//action属性
			detailForm.setAttribute("action","SchedulemodifyServlet");
			// フォームの位置まで画面をスクロール
            form.scrollIntoView({ behavior: 'smooth', block: 'start' });
            
        });
    });
});

//予定登録の曜日取得
document.addEventListener("DOMContentLoaded", () => {
	  const weekdays = ["日", "月", "火", "水", "木", "金", "土"];
	  const dateElements = document.querySelectorAll(".schedule-date");
	  
	  dateElements.forEach(el => {
	    const dateStr = el.textContent.trim();
	    if (!dateStr) return;
	    
	    const date = new Date(dateStr);
	    if (!isNaN(date)) {
	      const weekday = weekdays[date.getDay()];
	      el.nextElementSibling.textContent = `（${weekday}）`;
	    }
	  });
	});