var express = require('express');
const uuidv1 = require('uuid/v1');
var router = express.Router();
const {exec} = require('child_process');
const fs = require('fs');
// default options
/* GET home page. */
router.get('/', function (req, res, next) {
    res.render('index', {title: 'Express'});
});

router.post('/graph', (req, res, next) => {
    if (req.files.graph === undefined )
        return res.status(400).send('No file was uploaded.');

    let file = req.files.graph;
    let splitted = file.name.split('.');
    if(!splitted[splitted.length-1].includes('dot'))
        return res.status(400).send('No .dot file was uploaded.');

    const uuid = uuidv1();

    file.name = uuid + '.dot';

    console.log("filename: ",file.name)

    file.mv(`./files/${file.name}`, (error) => {
        if (error)
            return res.status(500).send('nope.');

        exec(`./graphviz/Graphviz2.38/bin/dot.exe -Tpng png -O ${uuid}.png ./files/${file.name}`, (err, stdout, stderr) => {
            const filepath = `./files/${file.name}.png`;

            console.log(stdout);
            console.log(stderr);

            fs.exists(filepath , function(exists){
                if (exists) {
                    res.writeHead(200, {
                        "Content-Type": "application/octet-stream",
                        "Content-Disposition": "attachment; filename=" + file.name + '.png'
                    });
                    return fs.createReadStream(filepath).pipe(res);
                } else {
                    res.writeHead(400, {"Content-Type": "text/plain"});
                    return res.end("ERROR File does not exist");
                }
            });
            }
        );
    });
});

module.exports = router;
