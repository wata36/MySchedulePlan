//class = "delete" の要素を取得する（querySelectorAll)
//そのボタンがsubmitされたタイミングでalertを表示する
//alertでOKが押されたらsubmitする

//document.addEventListener('DOMContentLoaded', function() {
//	const deleteForms = document.querySelectorAll('form[action="ScheduleDleateServlet"]');
//	deleteForms.forEach(form => {
//
//		form.addEventListener('submit', function(event) {
//		event.preventDefault();
//			
//			if (window.confirm('この項目を削除しますか？よろしいですか？')) {	
//				this.submit();
//				}
//	        });
//	    });
//	});
//	
	
//	const submit = document.getElementById('submit');
//
//const deleteButtons = document.querySelectorAll('.delete');
//
//submit.addEventListener('click', () => {
//	if(window.confirm('この項目を削除しますか？')){ 
//		 
//} else {
//  // 何もしない
//}

document.addEventListener('DOMContentLoaded', function() {
    
    // フォームを取得
    const deleteForms = document.querySelectorAll('form[action="ScheduleDleateServlet"]');

    deleteForms.forEach(form => {
        
        // async を追加
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