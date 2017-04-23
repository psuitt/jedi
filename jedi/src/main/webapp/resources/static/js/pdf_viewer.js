(function (global) {
    'use strict';

    function PdfViewer() {

        var RETRIEVE_DOC_URL = "/jedi/resources/static/pdf/CASE_MANAGEMENT_ORDER.pdf";
        var GET = "GET";

        var pdfDoc = null;

        function renderViewer() {
            var body = document.getElementsByTagName("body")[0];
            body.style.overflowY = "hidden";
            var outerContainer = createOuterContainer();
            var mainContainer = createMainContainer();
            outerContainer.appendChild(mainContainer);
            body.appendChild(outerContainer);
        }

        function createOuterContainer() {
            var outerContainer = document.createElement("div");
            outerContainer.id = "outerContainer";
            outerContainer.style.width = "100%";
            outerContainer.style.height = "100%";
            outerContainer.style.position = "inherit";
            return outerContainer;
        }

        function createMainContainer() {
            var mainContainer = document.createElement("div");
            mainContainer.id = "mainContainer";
            mainContainer.style.overflowY = "auto";
            mainContainer.style.position = "absolute";
            mainContainer.style.top = "0";
            mainContainer.style.right = "0";
            mainContainer.style.bottom = "0";
            mainContainer.style.left = "0";
            mainContainer.style.backgroundColor = "rgba(0,0,0,0.65)";
            mainContainer.appendChild(createToolbar());
            mainContainer.appendChild(createViewer());
            return mainContainer;
        }

        function createToolbar() {
            var toolbar = document.createElement("div");
            toolbar.id = "toolbar";
            toolbar.style.position = "fixed";
            toolbar.style.left = "0";
            toolbar.style.right = "0";
            toolbar.style.height = "48px";
            toolbar.style.zIndex = "9998";
            toolbar.style.background = "linear-gradient(to bottom,rgba(0,0,0,0.85) 0%,transparent 100%)";
            toolbar.appendChild(createToolbarViewer());
            return toolbar;
        }

        function createToolbarViewer() {
            var toolbarViewer = document.createElement("div");
            toolbarViewer.id = "toolbarViewer";
            toolbarViewer.style.height = "48px";
            toolbarViewer.appendChild(createToolbarViewerLeft());
            toolbarViewer.appendChild(createToolbarViewerRight());
            return toolbarViewer;
        }

        function createToolbarViewerLeft() {
            var toolbarViewerLeft = document.createElement("div");
            toolbarViewerLeft.id = "toolbarViewerLeft";
            toolbarViewerLeft.style.float = "left";
            toolbarViewerLeft.appendChild(createCloseIcon());
            return toolbarViewerLeft;
        }

        function createToolbarViewerRight() {
            var toolbarViewerRight = document.createElement("div");
            toolbarViewerRight.id = "toolbarViewerRight";
            toolbarViewerRight.style.float = "right";
            toolbarViewerRight.appendChild(createDownloadIcon());
            return toolbarViewerRight;
        }

        function createCloseIcon() {
            var closeIconContainer = document.createElement("div");
            closeIconContainer.id = "close";
            closeIconContainer.style.height = "36px";
            closeIconContainer.style.width = "36px";
            closeIconContainer.style.margin = "12px";
            closeIconContainer.style.color = "#fff";
            closeIconContainer.style.display = "inline-block";
            closeIconContainer.addEventListener("click", closeViewer);
            var closeIcon = document.createElement("i");
            closeIcon.className = "material-icons";
            closeIcon.innerHTML = "&#xE14C;";
            closeIconContainer.appendChild(closeIcon);
            return closeIconContainer;
        }

        function createDownloadIcon() {
            var downloadIconContainer = document.createElement("div");
            downloadIconContainer.id = "download";
            downloadIconContainer.style.height = "36px";
            downloadIconContainer.style.width = "36px";
            downloadIconContainer.style.margin = "12px";
            downloadIconContainer.style.color = "#fff";
            downloadIconContainer.style.display = "inline-block";
            downloadIconContainer.addEventListener("click", downloadPdf);
            var downloadIcon = document.createElement("i");
            downloadIcon.className = "material-icons";
            downloadIcon.innerHTML = "&#xE2C4;";
            downloadIconContainer.appendChild(downloadIcon);
            return downloadIconContainer;
        }

        function createViewer() {
            var viewer = document.createElement("div");
            viewer.id = "pdf-viewer";
            viewer.style.position = "absolute";
            viewer.style.top = "48px";
            viewer.style.left = "0";
            viewer.style.right = "0";
            viewer.style.bottom = "0";
            return viewer;
        }

        function getPdf() {
            var xhr = new XMLHttpRequest();
            xhr.open(GET, RETRIEVE_DOC_URL, true);
            xhr.responseType = "arraybuffer";
            xhr.onload = function(e) {
                if (this.status == 200) {
                    var blob = new Blob([this.response], {type:"application/pdf"});
                    var fileReader = new FileReader();
                    fileReader.readAsArrayBuffer(blob);
                    fileReader.onload = function() {
                        var typedarray = new Uint8Array(this.result);
                        PDFJS.getDocument(typedarray).then(function(_pdfDoc) {
                            pdfDoc = _pdfDoc;
                            renderViewer();
                            renderPDF();
                        });
                    };
                }
            };
            xhr.send();
        }

        function renderPDF() {
            for(var num = 1; num <= pdfDoc.numPages; num++){
                renderPage(num);
            }
        }

        function renderPage(num) {
            pdfDoc.getPage(num).then(function(page) {
                var div = document.createElement("div");
                div.className = "pageContainer";
                div.style.padding = "10px";
                div.style.margin = "0 auto";
                div.style.width = "800px";
                div.style.position = "relative";
                div.setAttribute("data-page-num", num);
                var canvas = document.createElement("canvas");
                canvas.className = "page";
                canvas.style.pointerEvents = "none";
                canvas.width = 800;
                var viewport = page.getViewport(canvas.width / page.getViewport(1.0).width);
                canvas.height = viewport.height;
                var renderContext = {
                    canvasContext: canvas.getContext("2d"),
                    viewport: viewport
                };
                page.render(renderContext);
                div.appendChild(canvas);
                document.getElementById("pdf-viewer").appendChild(div);
            });
        }

        function closeViewer() {
            var viewer = document.getElementById("outerContainer");
            if(viewer){
                viewer.parentNode.removeChild(viewer);
            }
            var body = document.getElementsByTagName("body")[0];
            body.style.overflowY = "";
        }

        function downloadPdf() {
            var xhr = new XMLHttpRequest();
            xhr.open(GET, RETRIEVE_DOC_URL, true);
            xhr.responseType = "arraybuffer";
            xhr.onload = function(e) {
                if (this.status == 200) {
                    var blob = new Blob([this.response], {type:"application/pdf"});
                    var link = document.createElement("a");
                    link.href = window.URL.createObjectURL(blob);
                    link.download = "Preview";
                    link.click();
                }
            };
            xhr.send();
        }

        this.renderPDFViewer = function() {
            getPdf();
        };

    }

    // Export
    global.PdfViewer = PdfViewer;

})(this);