const editor = new toastui.Editor({
  el: document.querySelector('#editor'),
  height: '600px',
  initialEditType: 'markdown',
  previewStyle: 'vertical'
});

function PostWrite__submit(form) {
  let PostWrite__submitDone = false;

  if (PostWrite__submitDone) {
    return;
  }

  form.subject.value = form.subject.value.trim();

  if (form.subject.value.length === 0) {
    warningModal('제목을 입력해주세요.');
    form.subject.focus();

    return;
  }

  const markdown = editor.getMarkdown();
  form.content.value = markdown.trim();

  if (form.content.value.length === 0) {
    warningModal('내용을 입력해주세요.');

    return;
  }

  PostWrite__submitDone = true;

  if (PostWrite__submitDone) {
    form.submit();
  }
}