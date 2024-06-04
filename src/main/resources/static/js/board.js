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
            location.replace('/board');
        }

        httpRequest('DELETE',`/board/${id}/del`, null, success, fail);
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