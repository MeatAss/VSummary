var simplemde = null;
$(document).ready(function() {
        simplemde = new SimpleMDE({
        autofocus: true,
        element: document.getElementById("textSummary"),
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
        previewRender: function(plainText) {
            return SimpleMDE.prototype.markdown(plainText); // Returns HTML from a custom parser
        },
        renderingConfig: {
            singleLineBreaks: false,
            codeSyntaxHighlighting: true,
        },
        shortcuts: {
            drawTable: "Cmd-Alt-T"
        },
        showIcons: ["code", "table"],
        spellChecker: false,
        status: false,
        status: ["autosave", "lines", "words", "cursor"], // Optional usage
        status: ["autosave", "lines", "words", "cursor", {
            className: "keystrokes",
            defaultValue: function(el) {
                this.keystrokes = 0;
                el.innerHTML = "0 Keystrokes";
            },
            onUpdate: function(el) {
                el.innerHTML = ++this.keystrokes + " Keystrokes";
            }
        }], // Another optional usage, with a custom status bar item that counts keystrokes
        styleSelectedText: false,
        tabSize: 4,
        toolbar: ["bold", "italic", "heading", "|", "quote", "unordered-list", "ordered-list", "|", "preview", "side-by-side", "fullscreen", "|", "guide"],
    });
});