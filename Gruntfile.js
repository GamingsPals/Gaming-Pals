module.exports = function(grunt) {

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        concat: {
            options: {
                separator: ';'
            },
            dist1: {
                src: [
                    'src/main/webapp/assets/js/app/init.js',
                    'src/main/webapp/assets/js/app/controllers.js',
                    'src/main/webapp/assets/js/app/services.js',
                    'src/main/webapp/assets/js/app/filters.js',
                    'src/main/webapp/assets/js/app/directives.js',
                    'src/main/webapp/assets/js/app/menu.js'

                ],
                dest: 'src/main/webapp/assets/js/app.js'
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-concat');


    grunt.registerTask('default', ['concat:dist1']);

};