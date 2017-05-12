app.service("PaginationService",function($location){
    this.paginations = {};

    this.generateIfNotExists = function (id,limit,url,items) {
        if(typeof this.paginations[id]==="undefined"){
            this.init(id,limit,url,items);
        }
    };

    this.getById = function(id){
        if(typeof this.paginations[id]!=="undefined"){
            return this.paginations[id];
        }

        return false;
    };

    this.init = function (id,limit,url,items) {
        let object = this;
        this.paginations[id] = {};
        this.paginations[id].limit = limit;
        this.paginations[id].records = items.length;
        this.paginations[id].numberPages = this.paginations[id].records;
        this.pagination[id].pages = [];
        for(let i =1;i<=this.paginations[id].numberPages;i++){
            this.pagination[id].pages.push(i);
        }
        if( url===true){
            if(typeof $location.search().page!=="undefined"){
                this.pagination[id].page = $location.search().page;
            }else{
                this.pagination[id].page = 1;
            }
        }else{
            if(typeof this.pagination[id].page==="undefined"){
                this.pagination[id].page = 1;
            }
        }
        this.paginations[id].changePage = function(page){
            object.paginations[id].page = page;
            if(object.paginations[id].url===true){
                $location.search("page",page);
            }
        };
    }


});


app.directive("gpPaginate",function($compile,$location,PaginationService){
    return{
        restrict: "AEC",
        scope:{
            "gpPaginate": "=",
            "gpPaginateLimit": "@",
            "gpPaginatePage": "@",
            "gpPaginateUrl": "@",
            "gpPaginateId" : "@"
        },
        link: function(scope,element,attrs){
            scope.$watchGroup(["qpPaginateScope"],(a)=>{
                scope.gpPaginatePage = parseInt(scope.gpPaginatePage);
                if(typeof a !=="undefined"){
                    scope.pagination = {};
                    PaginationService.generateIfNotExists(scope.gpPaginateId,
                    scope.gpPaginateLimit,scope.gpPaginateUrl,scope.gpPaginate);
                    let pagination = PaginationService.getById(scope.gpPaginateId);
                    let template =
                        `<li><a  href="#" ng-click="pagination.changePage('1')" ng-if="pagination.numberPages>1"
                        ng-class="(pagination.page=='1') ? 'active' : ''">
                            <i class="fa fa-angle-double-left" aria-hidden="true"></i></a> </li>
                        <li ng-repeat="page in pagination.pages track by $index" ng-class="(pagination.page==page) ? 'active' : ''">
                        <a href="#" ng-click="pagination.changePage(page)">{{page}}</a> </li>
                        <li><a href="#" ng-click="pagination.changePage(pagination.numberPages)" 
                        ng-if="pagination.numberPages>3"     ng-class="(pagination.page==pagination.numberPages) ? 'active' : ''">
                        <i class="fa fa-angle-double-right" aria-hidden="true"></i></a> </li>`;
                    scope.pagination.changePage = function(page){
                        scope.pagination.page = page;
                        if(scope.pagination.url===true){
                            $location.search("page",page);
                        }
                    };
                    element.html(template);
                    $compile(element.contents())(scope);
                }
            })
        }
    }
});


app.filter('paginate', function() {


    return function(input,pagination) {
        if(typeof input!=="undefined"){

            console.log(pagination);
        if(typeof pagination==="undefined"){
            pagination = {};
            pagination.page = 1;
            pagination.limit = 10;
        }
        let page = pagination.page;
        let limit =  pagination.limit;
        let min = (page-1)*limit;
        let max = (page-1)*limit+limit;
        max = Math.min(input.length,max);

        return input.slice(min,max);
        }
        return input;
    }

});