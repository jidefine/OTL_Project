class boardReadModal extends HTMLElement {
    /** - 작성자 : 유지오
     *
     * data-toggle="modal" data-target="#태그아이디"에서
     * data-target은 보여줄 태그 아이디입니다.
     *
     */
    connectedCallback() {
        const bno = this.getAttribute('board.bno');
        const boardTitle = this.getAttribute('board.boardTitle');
        const boardContent = this.getAttribute('board.boardContent');
        const nickname = this.getAttribute('board.nickname');
        const memberProfileImage = this.getAttribute('data-profile-image');

        this.innerHTML = `
            <!-- The Modal -->
            <div class="modal fade" id="boardReadModal" data-backdrop="static">
                <div class="modal-dialog" style="max-width: 60%;">
                    <div class="modal-content">

                        <!-- Modal Header -->
                        <div class="modal-header">
                            <h4 class="modal-title font-weight-bolder" style="color: #FD7B38;">자유게시판</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>

                        <!-- Modal Body -->
                        <div>
                            <form action="#" method="post">
                                <div class="modal-body" style="color: black;">
                                    <!-- 게시글 제목 -->
                                    <div class="board_title row mb-3">
                                    <span class="board-container nickname">${bno}</span>
                                        <label class="col-sm-3 col-form-label">제목</label>
                                        <div class="col-sm-11">
                                        <input type="text" class="form-control" value="${boardTitle}">
                                    </div>
                                    <!-- 작성자 프로필 이미지, 작성자, 작성일, 수정일-->
                                    <div class="board-container d-flex align-items-center justify-content-between"
                                         style="margin-bottom: 20px;">
                                        <!-- 작성자 프로필 이미지 -->
                                        <img src="${memberProfileImage}" alt="memberProfileImage" class="writer-container rounded-circle"
                                             style="width: 50px; height: 50px;">
                                        <!-- 작성자 -->
                                        <span class="board-container nickname">${nickname}</span>
                                        <!-- 작성일 -->
                                        <span class="board-container board_createDate" th:value="$">작성일</span>
                                        <!-- 수정일 -->
                                        <span class="board-container board_modDate" th:value="$">수정일</span>
                                    </div>
                                    <!--게시판 주의사항-->
                                    <div class="board_warning rounded p-2" style="margin-bottom: 20px; background-color: silver;">
                                        <div class="writer-container text-center">
                                            <span class="font-weight-bolder">※ 회원 간 불쾌한 언급은 서비스 이용에 불이익을 받게 됩니다. ※</span>
                                        </div>
                                    </div>
                                    <!-- 게시글 내용 -->
                                    <div class="board_content row mb-3">
                                        <label class="col-sm-2.5 col-form-label">내용</label>
                                        <div class="col flex-grow-1">
                                               <textarea type="text" class="form-control"
                                                         style="height: 30vh;" readonly>${boardContent}</textarea>
                                        </div>
                                    </div>

                                <!-- Modal Footer -->
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-success" data-dismiss="modal">수정하기</button>
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
           `;

        // 모달이 열릴 때마다 요청을 보내도록 수정
        this.addEventListener('show.bs.modal', () => {
            const bno = this.getAttribute('board.bno');
            fetch('/api/readBoard', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(bno) // bno를 요청 바디에 포함
            })
                .then(response => response.text())
                .then(data => {
                    console.log(data);
                    // 받은 데이터를 모달에 표시하는 등의 작업 수행
                    // 모달 내용을 여기에 삽입하는 방법도 있으나, 보통은 모달 내용을 가져온 다음에 삽입
                })
                .catch(error => console.error('Error:', error));
        });

        // 모달을 닫을 때마다 모달 내용 초기화
        this.addEventListener('hide.bs.modal', () => {
            // 모달 내용 초기화 작업 수행
        });
    }
}

customElements.define("custom-board-read-modal", boardReadModal);