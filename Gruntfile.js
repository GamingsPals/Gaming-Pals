module.exports = function(grunt) {

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        concat: {
            options: {
                separator: ';'
            },
            dist1: {
                src: [
                    'src/main/webapp/assets/js/showdown/showdown.min.js',
                    'src/main/webapp/assets/js/socket.io.js',
                    'src/main/webapp/assets/js/jquery/jquery.js',
                    'src/main/webapp/assets/js/flexslider/flexslider.js',
                    'src/main/webapp/assets/js/angular/angular.js',
                    'src/main/webapp/assets/js/angular/angular-route.js',
                    'src/main/webapp/assets/js/angular/ng-loading/loading-bar.min.js',
                    'src/main/webapp/assets/js/sweetalert/sweetalert.min.js',
                    'src/main/webapp/assets/js/angular/sweetalert/sweetalert.min.js',
                    'src/main/webapp/assets/js/angular/angular-sanitize.js',
                    'src/main/webapp/assets/js/angular/angular-messages.min.js',
                    'src/main/webapp/assets/js/angular/angular-cookies.min.js',
                    'src/main/webapp/assets/js/angular/scrollglue.js',
                    'src/main/webapp/assets/js/app/init.js',
                    'src/main/webapp/assets/js/app/routes.js',
                    'src/main/webapp/assets/js/app/router.js',
                    'src/main/webapp/assets/js/app/controllers/**',
                    'src/main/webapp/assets/js/app/services/**',
                    'src/main/webapp/assets/js/app/filters/**',
                    'src/main/webapp/assets/js/app/directives/**',
                    'src/main/webapp/assets/js/app/menu.js',
                    'src/main/webapp/assets/js/flexslider/flexslider.js',
                    'src/main/webapp/assets/js/angular/ng-dialog/js/ngDialog.min.js',

                ],
                dest: 'src/main/webapp/assets/js/app.js'
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-concat');

    grunt.registerTask('default', ['concat:dist1']);

};