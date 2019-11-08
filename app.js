


var app = angular.module('appLite', [
  'ngRoute',
  'empleadoControllers',
  'ui.bootstrap', 
  'ngCookies',
  'ngStorage',
  'ngMessages'
]);


app.config(['$routeProvider', '$locationProvider',
  function($routeProvider,$locationProvider) {
    $locationProvider.hashPrefix('');
    $routeProvider.
      when('/', {
        templateUrl: 'partials/home.html',
        controller: 'HomeController',
        method: 'GET',  
      }).
      when('/Home', {
        templateUrl: 'partials/home.html',
        controller: 'HomeController',
        method: 'GET',
        
      }).
      when('/ver/:id', {
        templateUrl: 'partials/ver.html',
        controller: 'EmpleadoVerCtrl',
        method: 'GET',
      }).
      when('/login', {
        templateUrl: 'login/login.view.html',
        controller: 'LoginController',
        method: 'GET',
      }).
      when('/register', {
        templateUrl: 'register/register.view.html',
        controller: 'RegisterController',
        method: 'GET',
      }).
      when('/register_a', {
        templateUrl: 'register/register.view_a.html',
        controller: 'RegisterController_a',
        method: 'GET',
      }).
      when('/home:user', {
        controller: 'HomeControllerUser',
        templateUrl: 'home/homeuser.php',
        method: 'GET',
      }).
      otherwise({
        redirectTo: '/',
        method: 'GET',
      });
  }]);


app.controller('empleadoControllers', function($scope){
  controllerPrincipal = $scope;
});

app.directive('tree', function() {
  return {
    restrict: "E",
    replace: true,
    scope: {
      tree: '='
    },
    templateUrl: 'template-ul.html'
  };
});

app.directive('leaf', function($compile,$timeout,$window) {
  return {
    restrict: "E",
    replace: true,
    scope: {
      leaf: "=",
    },
    templateUrl: 'template-li.html',
    link: function(scope, element, attrs) {
      if (angular.isArray(scope.leaf.subtree)) {
        element.append("<tree tree='leaf.subtree'></tree>");
        element.addClass('dropdown-submenu');
        $compile(element.contents())(scope);
      } else {
        element.bind('click', function() {
          console.log(scope.leaf.name);
          $timeout(function(){
            $window.location.reload();
          }, 50);
          
        });

      }
    }
  };
});
