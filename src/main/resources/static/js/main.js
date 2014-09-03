/**
 * Created by kayani on 2014-04-01.
 */

if (Kstream == null || typeof(Kstream) != "object") { var Kstream = new Object();}

Kstream.Browser = (function($, undefined){

    var browser = {

        currentPath: "",
        browseHistory: [],
        selectedNodes: {},
        nodeFileMap: {},
        modal: null,
        body: null,
        callback: null,
        allowMultiple: true,
        serviceUrl: "/admin/fs/ls",
        bodyClass: ".modal-body",
        pathDataKey: "data-path",

        create: function(modalId, allowMultiple){
            var b = Object.create(this);
            b.init(modalId, allowMultiple);
            return b;
        },

        init: function(modalId, allowMultiple){

          this.modal = $(modalId);
          this.currentPath = "";
          this.browseHistory.push(this.currentPath);
          this.allowMultiple = allowMultiple === undefined || allowMultiple;

          var body = this.modal.find(this.bodyClass);
          var ul = $("<ul></ul>");
          ul.addClass("bw-list");
          body.append(ul);
          this.body = ul;


          this.refresh();
          this.modal.addClass("bw-browser");
          var self = this;
          this.modal.find("button.bw-select-all").click(function(){self.selectAllNodes.call(self);});
          this.modal.find("button.bw-deselect-all").click(function(){self.deselectAllNodes.call(self);});

          this.modal.find("button.bw-submit").click(function(){
              if (self.callback === undefined || self.callback == null) return;
              self.callback.call(undefined, self.getSelectedFiles());
          });
        },

        onSubmit: function(callback){
            this.callback = callback;
        },

        browseDirectory: function(element){

            this.currentPath = this.nodeFileMap[this.hashFunc(element)].item.path;
            this.browseHistory.push(this.currentPath);

            this.refresh();
        },

        browseUp: function(){

            if (this.browseHistory.length == 1) return; // we are at the root

            this.browseHistory.pop();
            this.currentPath = this.browseHistory[this.browseHistory.length - 1];
            this.refresh();
        },

        selectFile: function(element){

            if (this.hashFunc(element) in this.selectedNodes){
                this.deselectAllNodes();
            }
            else {
                this.deselectAllNodes();
                this.selectNode(element);
            }
        },

        selectAdditionalFile: function(element){

            if (this.hashFunc(element) in this.selectedNodes){
                this.deselectNode(element);
            }
            else {
                this.selectNode(element);
            }
        },

        selectNode: function(element){
            $(element).addClass("bw-selected");
            this.selectedNodes[this.hashFunc(element)] = element;
        },

        deselectNode: function(element){
            $(element).removeClass("bw-selected");
            delete this.selectedNodes[this.hashFunc(element)];
        },

        deselectAllNodes: function(){

            for (var hash in this.selectedNodes){
                var node = this.selectedNodes[hash];
                $(node).removeClass("bw-selected");
            }
            this.selectedNodes = {};
        },

        selectAllNodes: function(){

            if (!this.allowMultiple) return;

            this.deselectAllNodes();

            for (var hash in this.nodeFileMap){
                var fileItem = this.nodeFileMap[hash].item;
                var node = this.nodeFileMap[hash].node;

                if (!fileItem.isDirectory){
                    $(node).addClass("bw-selected");
                    this.selectedNodes[hash] = node;
                }
            }

        },

        refresh: function(){
            var populateItems = this.populateItems;
            var currentPath = this.currentPath;
            var serviceUrl = this.serviceUrl;

            var self = this;
            $.ajax({
                url: serviceUrl,
                type: 'post',
                data: {path: currentPath},
                headers: Kstream.Csrf,
                dataType: 'json',
                success: function(data){
                    populateItems.call(self, data);
                }
            });
        },

        open: function(){
            this.modal.modal('show');
        },

        close: function(){
            this.modal.modal('hide');
        },

        getSelectedFiles: function(){
          var selectedFiles = [];
          for (var hash in this.selectedNodes){
            selectedFiles.push(this.nodeFileMap[hash].item);
          }

          return selectedFiles;
        },

        populateItems: function(data){
            this.nodeFileMap = {};
            this.selectedNodes = {};
            var nodeFileMap = this.nodeFileMap;
            this.body.empty();
            this.body.append(this.createUpElement());
            var browserDirectory = this.browseDirectory;
            var selectFile = this.selectFile;
            var selectAdditionalFile = this.selectAdditionalFile;
            var createFileElement = this.createFileElement;
            var self = this;

            $.each(data, function(index, value){
                var item = createFileElement.call(self, value);
                if (value.isDirectory){
                    item.click(function(){browserDirectory.call(self, this)});
                }
                else {
                    if (self.allowMultiple){
                        item.dblclick(function(){selectFile.call(self, this)});
                        item.click(function(){selectAdditionalFile.call(self, this)});
                    }
                    else {
                        item.click(function(){selectFile.call(self, this)});
                    }
                }
                nodeFileMap[self.hashFunc(item.get())] = {item: value, node: item.get()};
                self.body.append(item);
            });
        },

        createFileElement: function(dataItem){

            var itemElem = $("<li></li>");
            if (dataItem.isDirectory){
                itemElem.addClass("bw-dir");
            }
            else {
                itemElem.addClass("bw-file");
            }

            itemElem.text(dataItem.filename);

            return itemElem;
        },

        createUpElement: function(){
            var self = this;
            var browseUp = this.browseUp;
            var itemElem = $("<li></li>");

            itemElem.addClass("bw-up");

            $("<span></span>").addClass("glyphicon glyphicon-arrow-left").appendTo(itemElem);
            $("<span></span>").text(this.currentPath).addClass("bw-path").appendTo(itemElem);

            if (this.browseHistory.length == 1){
                itemElem.addClass("bw-disabled");
            }
            else {
                itemElem.click(function(){browseUp.call(self);});
            }

            return itemElem;
        },

        hashFunc: function(element){
            return $(element).text();
        }
    }

    return browser;
})(jQuery);

$(document).ready(function(){
    // prevent disable link clicks
    $("a.disabled").click(function(e){
        e.preventDefault();
    });

    // get headers
    var headers = {};
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    headers[header] = token;

    Kstream.Csrf = headers;



});