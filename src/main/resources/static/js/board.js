const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
    deleteButton.addEventListener('click', event => {
        let id = document.getElementById('article-id').value;
        function success() {
            alert('삭제가 완료되었습니다.');
            location.replace('/board');
        }

        function fail() {
            alert('삭제 실패했습니다.');
            // location.replace('/board');
        }
        if(confirm('정말 삭제하시겠습니까?')) {
            httpRequest('DELETE',`/board/${id}/del`, null, success, fail);
        }
    });
}

function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: { // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json',
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success();
        } else {
            return fail();
        }
        // const refresh_token = getCookie('refresh_token');
        // if (response.status === 401 && refresh_token) {
        //     fetch('/api/token', {
        //         method: 'POST',
        //         headers: {
        //             Authorization: 'Bearer ' + localStorage.getItem('access_token'),
        //             'Content-Type': 'application/json',
        //         },
        //         body: JSON.stringify({
        //             refreshToken: getCookie('refresh_token'),
        //         }),
        //     })
        //         .then(res => {
        //             if (res.ok) {
        //                 return res.json();
        //             }
        //         })
        //         .then(result => { // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
        //             localStorage.setItem('access_token', result.accessToken);
        //             httpRequest(method, url, body, success, fail);
        //         })
        //         .catch(error => fail());
        // } else {
        //     return fail();
        // }
    });
}

const createCommentButton = document.getElementById('create-comment-btn');

if (createCommentButton) {
    createCommentButton.addEventListener('click', function(event) {
        event.preventDefault();
        alert("success");

        const data = {
            postsId: $('#article-id').val(),
            comment: $('#comment').val()
        };

        if (!data.comment || data.comment.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            body = JSON.stringify({
                        comment: $('#comment').val()
                    });
                    function success() {
                        alert('등록 완료되었습니다.');
                        location.replace('/board/'+data.postsId);
                    };
                    function fail() {
                        alert('등록 실패했습니다.');
                        location.replace('/board/'+data.postsId);
                    };
                    httpRequest('POST', '/api/board/' + data.postsId + '/comments', body, success, fail);
        }
    });
}

function commentDelete(articleId, commentId, commentWriterId, sessionUserId) {
    // 본인이 작성한 글인지 확인
    if (commentWriterId !== sessionUserId) {
        alert("본인이 작성한 댓글만 삭제 가능합니다.");
    } else {
        const con_check = confirm("삭제하시겠습니까?");

        if (con_check) {
            function success() {
                alert('삭제가 완료되었습니다.');
                location.replace('/board/'+articleId);
            }

            function fail() {
                alert('삭제 실패했습니다.');
                location.replace('/board/'+articleId);
            }
            // 적절한 요청을 보내는 함수 (예: httpRequest)를 호출하여 댓글 삭제 요청을 서버에 전송합니다.
            httpRequest('DELETE',`/api/board/${articleId}/comments/${commentId}`, null, success, fail);
        }
    }
}
