const minlength = 25, maxlength = 1000;

function createMarkdown(textarea){
    simplemde = new SimpleMDE({
        element: textarea,
        forceSync: true,
        indentWithTabs: false,
        insertTexts: {
            horizontalRule: ["", "\n\n-----\n\n"],
            image: ["![](http://", ")"],
            link: ["[", "](http://)"],
            table: ["", "\n\n| Column 1 | Column 2 | Column 3 |\n| -------- | -------- | -------- |\n| Text     | Text      | Text     |\n\n"],
        },
        lineWrapping: false,
        parsingConfig: {
            allowAtxHeaderWithoutSpace: true,
            strikethrough: false,
            underscoresBreakWords: true,
        },
        placeholder: "Type here...",
        previewRender: getHTML,
        renderingConfig: {
            singleLineBreaks: false,
            codeSyntaxHighlighting: true,
        },
        shortcuts: {
            drawTable: "Cmd-Alt-T"
        },
        showIcons: ["code", "table"],
        spellChecker: false,
        status: ["autosave", "lines", "words", "cursor", {
            className: "keystrokes",
            defaultValue: function(el) {
                this.keystrokes = 0;
                el.innerHTML = "0 Keystrokes";
            },
            onUpdate: function(el) {
                el.innerHTML = ++this.keystrokes + " Keystrokes";
            }
        }],
        styleSelectedText: true,
        tabSize: 4,
        toolbar: ["bold", "italic", "heading", "|", "quote", "unordered-list", "ordered-list", "|", "preview", "side-by-side", "fullscreen", "|", "guide"],
    });
}

function checkValidityMarkdown(form) {
    let textarea = form.find('textarea');
    console.log(length);

    if (textarea.val().length < maxlength && textarea.val().length > minlength) {
        form.find('.CodeMirror').removeClass('border-danger');
        return true;
    }
    else {
        form.find('.CodeMirror').addClass('border-danger');
        return false;
    }
}

function getHTML(markdownText) {
    return SimpleMDE.prototype.markdown(markdownText);
}