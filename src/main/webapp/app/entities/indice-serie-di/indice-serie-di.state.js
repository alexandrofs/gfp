(function() {
    'use strict';

    angular
        .module('gfpApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('indice-serie-di', {
            parent: 'entity',
            url: '/indice-serie-di',
            data: {
                authorities: ['ROLE_USER','ROLE_ADMIN'],
                pageTitle: 'gfpApp.indiceSerieDi.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/indice-serie-di/indice-serie-dis.html',
                    controller: 'IndiceSerieDiController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('indiceSerieDi');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('indice-serie-di-detail', {
            parent: 'entity',
            url: '/indice-serie-di/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gfpApp.indiceSerieDi.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/indice-serie-di/indice-serie-di-detail.html',
                    controller: 'IndiceSerieDiDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('indiceSerieDi');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'IndiceSerieDi', function($stateParams, IndiceSerieDi) {
                    return IndiceSerieDi.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('indice-serie-di.new', {
            parent: 'indice-serie-di',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/indice-serie-di/indice-serie-di-dialog.html',
                    controller: 'IndiceSerieDiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                data: null,
                                taxaMediaAnual: null,
                                taxaSelic: null,
                                fatorDiario: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('indice-serie-di', null, { reload: true });
                }, function() {
                    $state.go('indice-serie-di');
                });
            }]
        })
        .state('indice-serie-di.edit', {
            parent: 'indice-serie-di',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/indice-serie-di/indice-serie-di-dialog.html',
                    controller: 'IndiceSerieDiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IndiceSerieDi', function(IndiceSerieDi) {
                            return IndiceSerieDi.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('indice-serie-di', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('indice-serie-di.import', {
            parent: 'indice-serie-di',
            url: '/import',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/indice-serie-di/indice-serie-di-import.html',
                    controller: 'IndiceSerieDiImportController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg'
                }).result.then(function() {
                    $state.go('indice-serie-di', null, { reload: true });
                }, function() {
                    $state.go('indice-serie-di');
                });
            }]
        })
        .state('indice-serie-di.delete', {
            parent: 'indice-serie-di',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/indice-serie-di/indice-serie-di-delete-dialog.html',
                    controller: 'IndiceSerieDiDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['IndiceSerieDi', function(IndiceSerieDi) {
                            return IndiceSerieDi.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('indice-serie-di', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
