% Haskell

# Haskell: A Pure Functional Language

Named after logician Haskell Curry

Pure and Lazy by default

Strongly and Statically Typed with Type Inferencing

Heavily influenced by ML

# Quick Terminology

When I say "Type" you think "Class"

When I say "Typeclass" you think "Interface/Mixin"

When I say "Monad" you go to sleep.

# QuickCheck - Property Based Testing

Would be nice to assert that a function has certain properties.

~~~ {.haskell}
idempotent_property = sort . sort == sort
~~~ {.haskell}

...But testing function equality is not practical

QuickCheck generates random data for us to test our properties

# Let's sort a list

~~~~ {.haskell}
mergeSort :: (a -> a -> Bool) -> [a] -> [a]
mergeSort pred = go
  where
    go []  = []
    go [x] = [x]
    go xs  = merge (go xs1) (go xs2)
      where (xs1,xs2) = split xs

    merge xs [] = xs
    merge [] ys = ys
    merge (x:xs) (y:ys)
      | pred x y  = x : merge xs (y:ys)
      | otherwise = y : merge (x:xs) ys
~~~~ {.haskell}


# The split function

The purpose of splitting is to produce two sublists of roughly equal
length, so that they can be merged (where the sorting occurs).

~~~~ {.haskell}
split :: [a] -> ([a],[a])
split []       = ([],[])
split [x]      = ([x],[])
split (x:y:zs) = (x:xs,y:ys)
  where (xs,ys) = split zs

sort :: Ord a => [a] -> [a]
sort = mergeSort (<)
~~~~

Now we can write our idempotency property test

~~~ {.haskell}
t_idempotent :: [Int] -> Bool
t_idempotent xs = sort (sort xs) == sort xs
~~~

# Let's give it a try

At the `ghci` prompt:

~~~~
>> import Test.QuickCheck
>> quickCheck t_idempotent
+++ OK, passed 100 tests.
>> verboseCheck t_idempotent
...lots of lists...
+++ OK, passed 100 tests.
~~~~

As the output suggests, QuickCheck generated 100 random lists, and
tested our property over them.

Yay!

# Algebraic Data Types

Haskell is not Object Oriented.

But how do I structure my data if I don't have classes?

Algebraic Data Types are the answer

# Binary Tree example

Define new type Tree with value constructors Node and Leaf

~~~~ {.haskell}
data Tree a = Node a (Tree a) (Tree a)
            | Leaf a
            deriving (Show)
~~~~

# Binary Tree example

We can deconstruct the Tree type into its value constructors.

And we can also use guards.

~~~~ {.haskell}
member :: Ord a => Tree a -> a -> Bool
member tree item =
  case tree of
    Leaf val -> val == item
    Node val left right | val == item -> True
    Node val left right | item < val -> member left item
    Node val left right | item > val -> member right item
~~~~

# Functors

Haskell makes many functional programming concepts first class.

A Functor is a typeclass consisting of one function fmap.

~~~~ {.haskell}
fmap :: (a -> b) -> c a -> c b

-- Should hold:
fmap id == id
fmap (f . g) == fmap f . fmap g
~~~~

# Functors

Here's the functor for our Tree

~~~~ {.haskell}
instance Functor Tree where
  fmap f tree =
    case tree of
      Leaf val -> Leaf (f val)
      Node val left right -> Node (f val) (fmap f left) (fmap f right)

~~~~

Let's try it out in GHCi

~~~~
>> let foo = Node 5 (Leaf 4) (Leaf 6)
>> fmap (\x -> x + 1) foo    	   -- Type Inferencing woohoo!
Node 6 (Leaf 5) (Leaf 7)
~~~~

# Conclusion

Haskell is a beautiful fuctional language

Haskell is very mathematical

Learning these functional Haskell concepts takes effort

But it will make you a better Java/Python/Ruby/Javascript programmer

Check out Guava/Apache Functions, Collections for Java.

Check out itertools, functools for Python.