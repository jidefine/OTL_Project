class boardModifyModal extends HTMLElement {
    /** - 작성자 : 유지오
     *
     * data-toggle="modal" data-target="#태그아이디"에서
     * data-target은 보여줄 태그 아이디입니다.
     *
     */
    connectedCallback() {
        const bno = this.getAttribute('board-bno');
        const boardTitle = this.getAttribute('board-boardTitle');
        const boardContent = this.getAttribute('board-boardContent');
        const nickname = this.getAttribute('board-nickname');
        const memberProfileImage = this.getAttribute('member-memberProfileImage');
        const regDate = this.getAttribute('board-regDate');
        const modDate = this.getAttribute('board-modDate');

        // console.log('bno : ' + bno);
        // console.log('boardTitle');
        // console.log('boardContent');
        // console.log('nickname');
        // console.log('MemberProfileImage');
        // console.log('regDate');
        // console.log('modDate');

        this.innerHTML = `
            <!-- The Modal -->
            <!-- 게시글 작성 모달 -->
            <form id="boardForm" action="/api/modifyBoard" method="POST">
                <div class="modal fade modifyModal" id="boardModifyModal-${bno}" data-backdrop="static">
                    <div class="modal-dialog" style="max-width: 60%;">
                        <div class="modal-content">
    
                            <!-- Modal Header -->
                            <div class="modal-header">
                                <h4 class="modal-title font-weight-bolder" style="color: #FD7B38;">자유게시판</h4>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
    
                            <!-- Modal Body -->
                            <div>
                                <div class="modal-body" style="color: black;">
                                        <!-- 게시글 제목 -->
                                        <div class="board_title row mb-3">
                                            <span id="boardHeader">${bno}</span>
                                            <label  class="col-sm-3 col-form-label">제목</label>
                                            <div class="col-sm-8">
                                                <input id="boardTitle" type="text" class="form-control" placeholder="제목을 입력하세요"
                                                value="${boardTitle}">
                                            </div>
                                        </div>
                                        <!-- 작성자 프로필 이미지, 작성자, 작성일, 수정일-->
                                        <div class="board-container d-flex align-items-center justify-content-between"
                                             style="margin-bottom: 20px;">
                                            <!-- 작성자 프로필 이미지 -->
                                            <img id="memberProfileImage" src="${memberProfileImage}" alt="memberProfileImage" class="writer-container rounded-circle"
                                                 style="width: 50px; height: 50px;">
                                            <!-- 작성자 -->
                                            <span id="nickname" class="board-container nickname">${nickname}</span>
                                            <!-- 작성일 -->
                                            <span id="regDate" class="board-container board_create_date">${regDate}</span>
                                            <!-- 수정일 -->
                                            <span id="modDate" class="board-container board_mod_date">${modDate}</span>
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
                                                   <textarea id="boardContent" type="text" class="form-control"
                                                             style="height: 30vh;">${boardContent}</textarea>
                                            </div>
                                        </div>
    
                                        <!-- Modal Footer -->
                                        <div class="modal-footer">
                                            <button id="boardModifyFinishBtn" type="submit" class="btn btn-success" data-dismiss="modal">수정완료</button>
                                            <button id="boardDeleteBtn" type="button" class="btn btn-success" data-dismiss="modal">삭제하기</button>
                                            <button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
                                        </div>
                                </div>
                            </div>
                         </div>
                    </div>
                </div>
            </form>
        `;

        // 게시글 정보 채우기
        $('#boardModifyModal-' + bno).on('show.bs.modal', function (e) {
            $(this).find('#boardTitle').val(boardTitle);
            $(this).find('#boardContent').val(boardContent);
        });

        // 1: 게시글 수정
        const form = this.querySelector("#boardForm");

        form.addEventListener('submit', (event) => {
            event.preventDefault();
            const formData = {
                bno: bno,
                boardTitle: form.querySelector("#boardTitle").value,
                boardContent: form.querySelector("#boardContent").value
            };
            $.ajax({
                type: 'POST',
                url: '/api/modifyBoard',
                data: JSON.stringify(formData),
                contentType: 'application/json',
                success: function (response) {
                    console.log("AJAX 요청 성공: ", response);
                    alert('게시글이 성공적으로 수정되었습니다.');
                    location.reload();
                },
                error: function (xhr, status, error) {
                    console.error("AJAX 요청 실패: ", error);
                    alert('게시글 수정에 실패했습니다: ' + error);
                }
            });
        });
    }
}

customElements.define("custom-board-modify-modal", boardModifyModal);