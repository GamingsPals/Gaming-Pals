app.service("PaginationService",function($location){
    this.paginations = {};

    this.setPaginate = function (id,limit,url,items) {
            this.init(id,limit,url,items);
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
        this.paginations[id].records = (typeof items!=="undefined") ? items.length : 0;
        this.paginations[id].numberPages = Math.ceil(this.paginations[id].records/limit);
        this.paginations[id].id = id;
        this.paginations[id].url =  Boolean(url);
        this.setupUrlPages();
        this.calculatePages(id);
    };

    this.setupUrlPages = function(){
        if (this.paginations[id].url === true) {
            if (typeof $location.search().page !== "undefined") {
                this.paginations[id].page = parseInt($location.search().page);
            } else {
                this.paginations[id].page = 1;
            }
        } else {
            if (typeof this.paginations[id].page === "undefined") {
                this.paginations[id].page = 1;
            }
        }
    };

    this.calculatePages = function(id){
        this.paginations[id].pages = [];
        let medium = this.paginations[id].page;
        let min = Math.max(1,medium-3);
        let max = Math.min(medium+3,this.paginations[id].numberPages);
        for (let i = min; i <= max; i++) {
            this.paginations[id].pages.push(i);
        }
    };

    this.changePage = function(page,id){
        this.paginations[id].page = page;
        if(this.paginations[id].url===true){
            $location.search("page",page);
        }
        this.calculatePages(id);
    };

    this.isActive = function(active,id){
        return active === this.paginations[id].page;
    }


});


app.directive("gpPaginate",function($compile,$location,PaginationService){
    return{
        restrict: "AEC",
        scope:{
            "gpPaginateItems": "=",
            "gpPaginateLimit": "@",
            "gpPaginatePage": "@",
            "gpPaginateUrl": "@",
            "gpPaginateId" : "@"
        },
        link: function(scope,element,attrs){
            scope.$watchGroup(["gpPaginatePage","gpPaginateItems"],(a)=>{
                scope.gpPaginatePage = parseInt(scope.gpPaginatePage);
                if(typeof a !=="undefined"){
                    PaginationService.setPaginate(scope.gpPaginateId,
                    scope.gpPaginateLimit,scope.gpPaginateUrl,scope.gpPaginateItems);
                    let pagination = PaginationService.getById(scope.gpPaginateId);
                    let id = scope.gpPaginateId;
                    let template =
                        `<li>
                             <a  href="#" ng-click="pagination.changePage(1,'${id}')" 
                             ng-if="pagination.getById('${id}').numberPages>1"
                             ng-class="(pagination.isActive(1,'${id}')) ? 'active' : ''">
                            <i class="fa fa-angle-double-left" aria-hidden="true"></i>
                            </a> 
                            </li>
                            
                        <li ng-repeat="page in pagination.getById('${id}').pages track by $index" 
                        ng-class="(pagination.isActive(page,'${id}')) ? 'active' : ''">
                        <a href="#" ng-click="pagination.changePage(page,'${id}')">
                        {{page}}
                        </a> 
                        </li>
                        
                        <li>
                        <a href="#" 
                        ng-click="pagination.changePage(pagination.getById('${id}').numberPages,'${id}')" 
                        ng-if="pagination.getById('${id}').numberPages>3"     
                        ng-class="(pagination.isActive(pagination.getById('${id}').numberPages,'${id}')) ? 'active' : ''">
                        <i class="fa fa-angle-double-right" aria-hidden="true"></i></a> 
                        </li>`;

                    element.html(template);
                    $compile(element.contents())(scope.$parent);
                }
            })
        }
    }
});


app.filter('paginate', function(PaginationService) {


    return function(input,pagination) {
        if(typeof input!=="undefined"){
        pagination = PaginationService.getById(pagination);
        if(typeof pagination==="undefined"){
            pagination = {};
            pagination.page = 1;
            pagination.limit = 10;
        }
        let page = parseInt(pagination.page);
        let limit =  parseInt(pagination.limit);
        let min = (page-1)*limit;
        let max = (page-1)*limit+limit;
        max = Math.min(input.length,max);

        return input.slice(min,max);
        }
        return input;
    }

});