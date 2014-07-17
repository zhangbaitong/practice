var $p = require('procstreams');
//cat readme.md | wc -l
$p('cat readme.md').pipe('wc -l')
    .data(function(err, stdout, stderr) {
        // handle error

        console.log(stdout + ''); // prints number of lines in the file lines.txt
    });

//$p('mkdir foo')
//    .and('cp file.txt foo/')
//    .and('rm file.txt')
//    .on('exit', function() {
//        console.log('done');
//    });
