var empleadoControllers = angular.module('empleadoControllers', []);
var rute = 'http://localhost:50/Libreria/';
'use strict';

empleadoControllers.controller('LoginController', ['$scope','$location', 'AuthenticationService','$http','$timeout',function($scope,$location,AuthenticationService,$http,$timeout) {
    $scope.saveduser = localStorage.getItem('todosuser');
    $scope.SesionUser = JSON.parse($scope.saveduser);
    //console.log("nuevo nuevo",JSON.stringify($scope.SesionUser));
    for(var i in $scope.SesionUser){
        //console.log($scope.SesionUser[i]['email']);
        $scope.Email = $scope.SesionUser[i]['email'];
        $scope.Rol = $scope.SesionUser[i]['rol'];
    }
    var iduser = document.getElementById("welcome");
    var idlogin = document.getElementById("login");
    if($scope.Rol == '1' || $scope.Rol == '2'){
        $scope.logged = false;
        $scope.unlogged = true;
    }else{
        $scope.logged = true;
        $scope.unlogged = false;
    }
    $scope.login = function () {
        $scope.dataLoading = true;
        $timeout(function(){
            var model = {
                email: $scope.email,
                password: $scope.password,
            };
            var dataof = JSON.stringify(model);
            $http.post(rute+'api/?a=Login',dataof).then(function successCallback(response) {
                var consulta = response.data;
                if(consulta == false){
                    $scope.error = 'Email or password is incorrect';
                    $scope.dataLoading = false;
                }else{
                    $scope.saveduser = localStorage.getItem('todosuser');
                    $scope.todosuser = (localStorage.getItem('todosuser')!==null) ? JSON.parse($scope.saveduser) : [ ];
                    $scope.todosuser.push(consulta);
                    localStorage.setItem('todosuser', JSON.stringify($scope.todosuser));
                    $timeout(function(){
                        $scope.dataLoading = false;
                        location.reload();
                    }, 50);
                }
            }, function errorCallback(response) {
                $scope.error = 'Email or password is incorrect';
                $scope.dataLoading = false;
            });
        }, 1000);
    };   
}]);

empleadoControllers.controller('CustomerList', ['$scope','$http','$timeout','$window', function($scope,$http,$timeout,$window){
    $scope.dataLoading = true;
    $http.post(rute+'api/?a=listar').then(function successCallback(response) {
        $scope.dataLoading = true; 
        $timeout(function(){ 
            $scope.dataLoading = false;   
            $scope.model = response.data;
        }, 100);
    }, function errorCallback(response) {
        $scope.error = 'Error 808';
        $scope.dataLoading = false;
    });
    $scope.retirar = function(id){
        if(confirm('Esta seguro de realizar esta accion?')){
            $scope.dataLoading = true;
            $http.get(rute+'api/?a=eliminar&id='+ id).then(function(response){
                $scope.dataLoading = true;
                $timeout(function(){
                    location.reload();
                    $scope.dataLoading = false;
                }, 50);  
            }, function errorCallback(response) {
                $timeout(function(){
                    location.reload();
                    $scope.dataLoading = false;
                }, 50);
            });
        };
    };  
}]);

empleadoControllers.controller('RegisterController', ['$scope','$http','$timeout','$window','$location', function($scope,$http,$timeout,$window,$location){
    $scope.saveduser = localStorage.getItem('todosuser');
    $scope.SesionUser = JSON.parse($scope.saveduser);
    //console.log("nuevo nuevo",JSON.stringify($scope.SesionUser));
    for(var i in $scope.SesionUser){
        //console.log($scope.SesionUser[i]['email']);
        $scope.Email = $scope.SesionUser[i]['email'];
        $scope.Rol = $scope.SesionUser[i]['rol'];
    }
    console.log($scope.Rol);
    var iduser = document.getElementById("welcome");
    var idlogin = document.getElementById("login");
    if($scope.Rol == '1' || $scope.Rol == '2'){
        $scope.logged = false;
        $scope.unlogged = true;
    }else{
        $scope.logged = true;
        $scope.unlogged = false;
    }
    $scope.register = function(){
            var model = {
                Nombre: $scope.Nombre,
                Apellido: $scope.Apellido,
                email: $scope.email,
                contra: $scope.contra,
            };
            var dataof = JSON.stringify(model);
            $http.post(rute+'api/?a=valemail',dataof).then(function successCallback(response) {
                var consulta = response.data;
                if(consulta != false){
                    $scope.error = 'This email is already in use';
                }else{
                    $http.post(rute+'api/?a=registrar',dataof).then(function successCallback(response) {   
                        $scope.dataLoading = true;
                        $timeout(function(){
                            $location.path('/login');
                            $scope.dataLoading = false;
                        }, 500);    
                    }, function errorCallback(response) {
                        $scope.dataLoading = true;
                        $timeout(function(){
                            $location.path('/login');
                            $scope.error = 'Error 505';
                            $scope.dataLoading = false;
                        }, 500);  
                    });
                }
            }, function errorCallback(response) {
                $scope.error = 'Information is incorrect';
            });   
    };
}]);


empleadoControllers.controller('RegisterController_a', ['$scope','$http','$timeout','$window','$location', function($scope,$http,$timeout,$window,$location){
    $scope.saveduser = localStorage.getItem('todosuser');
    $scope.SesionUser = JSON.parse($scope.saveduser);
    //console.log("nuevo nuevo",JSON.stringify($scope.SesionUser));
    for(var i in $scope.SesionUser){
        //console.log($scope.SesionUser[i]['email']);
        $scope.Email = $scope.SesionUser[i]['email'];
        $scope.Rol = $scope.SesionUser[i]['rol'];
    }
    console.log($scope.Rol);
    var iduser = document.getElementById("contentlist");
    $scope.rutaroluser = '';
    if($scope.Rol == '1'){
        console.log('user rol 1');
        $scope.seetoadmin = true;
    }else{
        console.log('user rol 2');
        $scope.seetoadmin = false;
    }
        $scope.register = function(){
            $scope.dataLoading = true;
                var model = {
                    Nombre: $scope.Nombre,
                    Apellido: $scope.Apellido,
                    email: $scope.email,
                    contra: $scope.contra,
                };
                var dataof = JSON.stringify(model);
                $http.post(rute+'api/?a=valemail',dataof).then(function successCallback(response) {
                    var consulta = response.data;
                    if(consulta != false){
                        $scope.error = 'This email is already in use';
                    }else{
                        $http.post(rute+'api/?a=registrar',dataof).then(function successCallback(response) {   
                            $scope.dataLoading = true;
                            $timeout(function(){
                                location.reload();
                                $scope.dataLoading = false;
                            }, 50);
                            
                        }, function errorCallback(response) {
                            $timeout(function(){
                                location.reload();
                                $scope.dataLoading = false;
                            }, 50);
                        });
                    }
                }, function errorCallback(response) {
                    $scope.error = 'Information is incorrect';
                });   
        };
}]);


empleadoControllers.controller('HomeControllerUser', ['$scope','$location','$http','$window','$timeout',function($scope,$location,$http,$window,$timeout) {
    console.log("activo");
    $scope.saveduser = localStorage.getItem('todosuser');
    $scope.SesionUser = JSON.parse($scope.saveduser);
    console.log("nuevo nuevo",$scope.SesionUser);
    for(var i in $scope.SesionUser){
        $scope.Email = $scope.SesionUser[i]['email'];
        $scope.Rol = $scope.SesionUser[i]['rol'];
    }
    console.log($scope.Rol);
    if($scope.Rol == '1' || $scope.Rol == '2'){
        $scope.logged = false;
        $scope.unlogged = true;
    }else{
        $scope.logged = true;
        $scope.unlogged = false;
    }
    $scope.CloseSession = function(){
        $scope.dataLoading = true;
        $timeout(function(){
            localStorage.removeItem('todosuser');
            $http.post(rute+'api/?a=Logout').then(function successCallback(response) {
                $timeout(function(){
                    $scope.dataLoading = false;
                    location.reload();
                }, 50);     
            }, function errorCallback(response) {
                $scope.dataLoading = true;
                $scope.error = 'No User';
            });  
        }, 1000);
    };
}]);

