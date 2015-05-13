http://marcelkornblum.com/2013/05/24/setting-up-the-lisp-repl-in-sublime-text-2-on-os-x/

Setting up the Lisp REPL in Sublime Text 2 on OS X

Posted on May 24, 2013 by Marcel Kornblum
If the title of this post is all gibberish to you, you have a stark choice: turn back now or read the next three paragraphs before deciding (and in all likelihood turning back then). If you know what the title is talking about you may as well skip the next 3 paragraphs :)

Lisp is a programming language. It’s pretty old as these things go – it was invented in 1958 – and I first learned it at university where it was in widespread use on the AI course. It broke my mind then, but I was recently reading an interesting essay

(http://paulgraham.com/avg.html) by Paul Graham, which took me by surprise in its eloquent argument that Lisp is perhaps the most powerful programming language of all. I decided to relearn it by reading this online book

(http://www.gigamonkeys.com/book/practical-a-simple-database.html) (after(http://psg.com/~dlamkins/sl/contents.html) some(http://www.amazon.com/dp/0262560992/?tag=stackoverfl08-20) research(http://stackoverflow.com/questions/398579/whats-the-best-way-to-learn-lisp)).

One of the first things you do in the book is set up the REPL (read-eval-print-loop) – it’s basically a console where you can interactively run your code, a bit like the JS console in the dev tools section of your browser. I hadn’t realised, but this awesomeness is actually available for tons of languages. It’s awesome by the way, because you can experiment and fiddle and debug things all while you’re working on them, without saving and reloading and compiling and all that other annoying stuff; it helps you get things working quicker.

Sublime Text 2 is simply a code editor that’s fast, powerful, extensible, customisable and hugely used these days. The whole reason I’m writing this post is because I wanted to code my Lisp in ST2 rather than emacs which the book suggests, because I have a religious aversion to emacs :)
http://xkcd.com/378/

Lisp REPL

OK, down to brass tacks: to install Lisp and the REPL in ST2 I had to first install Lisp on the machine. I used SBCL

（http://www.sbcl.org/platform-table.html） which seems to be widely used and respected. I downloaded the package and compiled it using the install shell script (inside the downloaded folder) which made it a one step process once I’d installed Xcode’s command line tools (the machine I’m using didn’t have make – you find the tools here

（https://developer.apple.com/downloads/index.action#） after login and through searching. Thanks Apple.)

With SBCL installed you do actually have a REPL now, but it’s not the handiest thing when you have to copy/paste your whole file(s) from Sublime to Terminal every time you want to try anything out.

Sublime Text 2

So onto Sublime. If you’ve not used it before, the first thing you should probably do is install Package Control

（http://wbond.net/sublime_packages/package_control/installation）, which basically makes it a snap to install pretty much any extra feature you like. At this point, you can (optionally) go down a wormhole of styling

（https://github.com/daylerees/colour-schemes）, fonts（http://sourceforge.net/projects/sourcecodepro.adobe/?source=dlp） and all sorts of options（http://drewbarontini.com/setup/sublime-text/） to make your Sublime experience more pleasant.

SublimeREPL

After that diversion, go ahead and install SublimeREPL

（https://github.com/wuub/SublimeREPL） into your editor using Package Control. SublimeREPL gives you a REPL as a ‘file’ inside ST2 itself, along with keyboard shortcuts to move lines, selections or whole files into the console tab – and when you’re typing in there you get Sublime’s full autocomplete and other functionality – which is great! It has a host of supported languages – perhaps most notably Python, allowing you to load up the virtualenv of your choice – woop!

SublimeREPL Lisp support

Ok so now we have everything we need, but Sublime doesn’t know about the Lisp installation on the machine, and neither does SublimeREPL. First thing, therefore, is to edit your Lisp package in Sublime to show it where to find SBCL (I figured this out thanks to this helpful post)（http://stackoverflow.com/questions/10197787/build-lisp-code-in-sublimetext2）. To quote pcmind（http://stackoverflow.com/users/600207/pcmind）:

In ST2 go to Preferences/Browse Packages, go to Lisp, add a new file Lisp.sublime-settings with:

{
    "cmd": ["sbcl", "--script", "$file"],
    "working_dir": "${project_path:${folder}}",
    "selector": "source.lisp",
    "osx":
    {
        "cmd": ["/opt/local/bin/sbcl", "--script", "$file"]
    },
    // exemple in windows with CLISP
    "windows":
    {
        "cmd": ["clisp", "$file"]
    },
    // exemple in windows with SBCL
    // "windows":
    // {
    //  "cmd": ["sbcl", "--script", "$file"]
    // }

	"extensions": ["lisp", "scm", "ss", "cl"]
}
Now Sublime knows about SBCL, but SublimeREPL doesn’t yet. For this you need to edit the Packages/Default/Default.sublime-commands and Packages/SublimeREPL/config/Lisp/Main.sublime-menu files to tell it to support Lisp, show it on the menu, and connect to the Lisp installation you’ve just told Sublime about. I found out about these through finding Jeff Thompson（https://github.com/jwthomp）‘s github repo（https://github.com/jwthomp/my-favorite-things/tree/master/Sublime%202/Lisp） with his settings in.

in Default.sublime-commands, add:

{
    "caption": "SublimeREPL: Lisp",
    "command": "run_existing_window_command", "args":
    {
        "id": "repl_lisp",
        "file": "config/Lisp/Main.sublime-menu"
    }
}
and in Main.sublime-menu add:

[
     {
        "id": "tools",
        "children":
        [{
            "caption": "SublimeREPL",
            "mnemonic": "r",
            "id": "SublimeREPL",
            "children":
            [
                {"command": "repl_open", 
                 "caption": "Lisp",
                 "id": "repl_lisp",
                 "mnemonic": "q",
                 "args": {
                    "type": "subprocess",
                    "encoding": "utf8",
                    "cmd": ["sbcl", "-i"],
                    "cwd": "$file_path",
                    "external_id": "lisp",
                    "syntax": "Packages/Lisp/Lisp.tmLanguage"
                    }
                }
            ]   
        }]
    }
]
You can check out my own (very new) settings repo（https://github.com/marcelkornblum/settings） I’ve made, where I’ve put these files in the right place for my OS X installation of ST2.

Testing it out

So now you should be all set up. Try it out – make a new file in ST2, add the following to it:

(defun hello-world () (format t "hello world"))
and save it with the “.lisp” extension; you should first of all notice that ST2 recognises the file and gives you code highlighting.

Now open the SublimeREPL Lisp window using Tools > SublimeREPL > Lisp. You can put it in a new pane within ST2  by going to View > Layout > Rows: 2.

Put your focus back into your lisp file, and use the menu Tools > sublimeREPL > Eval in REPL > file, or the keyboard shortcut ^,,f (which I had to look up – it’s ‘Control’ and the comma key followed by ‘F’ on OS X). You should see the REPL respond with output something like the following, as it accepts the function definition:

*
HELLO-WORLD
*
Note: the * character is your command prompt. Now put your cursor at the end of the REPL and call your function by typing the following (and watch as Sublime’s auto-complete kicks in!)

(hello-world)
and you should see something like:

* (HELLO-WORLD)
hello world
NIL
*
- in which case you’re up and running – now go play!

(oh one last note, if you do run into an error and the REPL enters debug mode, use ’0′ (zero) to exit and go back to your prompt.)

