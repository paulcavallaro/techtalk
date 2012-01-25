-- Merge Sort and Quick Check

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

split :: [a] -> ([a],[a])
split []       = ([],[])
split [x]      = ([x],[])
split (x:y:zs) = (x:xs,y:ys)
  where (xs,ys) = split zs


-- Point free notation (Hey there's no variable for the list to be sorted here!)
sort :: Ord a => [a] -> [a]
sort = mergeSort (<)

t_idempotent :: [Int] -> Bool
t_idempotent xs = sort (sort xs) == sort xs

t_idempotent_str :: String -> Bool -- String is a type synonym for [Char]
t_idempotent_str xs = sort (sort xs) == sort xs

-- Try it out in GHCi if you've cabal installed QuickCheck
-- > import Test.QuickCheck
-- > verboseCheck t_idempotent


-- Binary Trees are easy peasy

data Tree a = Node a (Tree a) (Tree a)
            | Leaf a
            deriving (Show)

member :: Ord a => Tree a -> a -> Bool
member tree item =
  case tree of
    Leaf val -> val == item
    Node val left right | val == item -> True
    Node val left right | item < val -> member left item
    Node val left right | item > val -> member right item

-- Haskell has some infix notation. Try this out in GHCi
-- > Node 5 (Leaf 4) (Leaf 6) `member` 4
-- True

instance Functor Tree where
  fmap f tree =
    case tree of
      Leaf val -> Leaf (f val)
      Node val left right -> Node (f val) (fmap f left) (fmap f right)

-- Try it out in GHCi
-- > let foo = Node 5 (Leaf 4) (Leaf 6)
-- > fmap (\x -> x + 1) foo
-- Node 6 (Leaf 5) (Leaf 7)