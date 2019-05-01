(function() {
    'use strict';

    angular
        .module('gfpApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('instituicao', {
            parent: 'entity',
            url: '/instituicao',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gfpApp.instituicao.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/instituicao/instituicaos.html',
                    controller: 'InstituicaoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('instituicao');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('instituicao-detail', {
            parent: 'instituicao',
            url: '/instituicao/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'gfpApp.instituicao.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/instituicao/instituicao-detail.html',
                    controller: 'InstituicaoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('instituicao');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Instituicao', function($stateParams, Instituicao) {
                    return Instituicao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'instituicao',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('instituicao-detail.edit', {
            parent: 'instituicao-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/instituicao/instituicao-dialog.html',
                    controller: 'InstituicaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Instituicao', function(Instituicao) {
                            return Instituicao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('instituicao.new', {
            parent: 'instituicao',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/instituicao/instituicao-dialog.html',
                    controller: 'InstituicaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('instituicao', null, { reload: 'instituicao' });
                }, function() {
                    $state.go('instituicao');
                });
            }]
        })
        .state('instituicao.edit', {
            parent: 'instituicao',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/instituicao/instituicao-dialog.html',
                    controller: 'InstituicaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Instituicao', function(Instituicao) {
                            return Instituicao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('instituicao', null, { reload: 'instituicao' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('instituicao.delete', {
            parent: 'instituicao',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/instituicao/instituicao-delete-dialog.html',
                    controller: 'InstituicaoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Instituicao', function(Instituicao) {
                            return Instituicao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('instituicao', null, { reload: 'instituicao' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
