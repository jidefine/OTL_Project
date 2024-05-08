class boardCreateModal extends HTMLElement {
    /** - 작성자 : 유지오
     *
     * data-toggle="modal" data-target="#태그아이디"에서
     * data-target은 보여줄 태그 아이디입니다.
     *
     */
    connectedCallback() {
        this.innerHTML = `
            <!-- The Modal -->
            <div class="modal fade" id="boardCreateModal" data-backdrop="static">
                <div class="modal-dialog" style="max-width: 60%;">
                    <div class="modal-content">

                        <!-- Modal Header -->
                        <div class="modal-header">
                            <h4 class="modal-title font-weight-bolder" style="color: #FD7B38;">자유게시판</h4>
                            <button type="button" class="close" data-dismiss="modal">&times</button>
                        </div>

                        <!-- Modal Body -->
                        <div>
                            <form action="#" method="post">
                                <div class="modal-body" style="color: black;">

                                    <!-- 게시글 제목 -->
                                    <div class="board_title row mb-3">
                                        <label class="col-sm-3 col-form-label">제목</label>
                                        <div class="col-sm-11">
                                            <input type="text" class="form-control" placeholder="제목을 입력하세요">
                                        </div>
                                    </div>
                                    <!-- 작성자 프로필 이미지, 작성자, 작성일, 수정일-->
                                    <div class="board-container d-flex align-items-center justify-content-between"
                                         style="margin-bottom: 20px;">
                                        <!-- 작성자 프로필 이미지 -->
                                        <img src="" alt="profillll" class="writer-container rounded-circle"
                                             style="width: 50px; height: 50px;">
                                        <!-- 작성자 -->
                                        <span class="board-container email">작성자이메일</span>
                                        <!-- 작성일 -->
                                        <span class="board-container board_createDate">작성일</span>
                                        <!-- 수정일 -->
                                        <span class="board-container board_modDate">수정일</span>
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
                                                         style="height: 30vh;" id="board content"
                                                         value="본문을 입력하세요.">
                                               </textarea>
                                        </div>
                                    </div>

                                <!-- Modal Footer -->
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-primary" data-dismiss="modal">작성하기</button>
                                    <button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
           `;
    }
}

customElements.define("custom-board-create-modal", boardCreateModal);