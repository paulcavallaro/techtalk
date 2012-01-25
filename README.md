# Haskell TechTalk

Generate slides from slides dir with:

    $ cabal install pandoc -fhighlighting
    $ cd slides
    $ pandoc --data-dir=css --offline -s -t slidy -o techtalk.html techtalk.md