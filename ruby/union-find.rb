class QuickFind
    attr_accessor :id

    def initialize(n = 10)
        @id = []
        (0..n-1).each {|i| @id[i] = i}
    end

    def find(p)
        id[p]
    end

    def union(p, q)
        p_id = find p.to_i
        q_id = find q.to_i
        return if p_id == q_id

        (0..id.size-1).each {|i|
            id[i] = q_id if (id[i] == p_id) 
        }
    end
end

class WeightedQuickUnion
    attr_accessor :id, :sz

    def initialize
        @count = @n = 10
        @id, @sz = [], []
        
        (0..@n-1).each {|i| @sz[i] = 1}
        (0..@n-1).each {|i| @id[i] = i}
        
    end
    
    def find(p)
        while p != id[p] do 
            p = id[p]
        end
        p
    end

    def union(p, q)
        i, j = find(p.to_i), find(q.to_i)
        return if i == j

        if sz[i] < sz[j] 
            id[i] = j; sz[j] += sz[i]        
        else               
            id[j] = i; sz[i] += sz[j]
        end 

        @count-=1
    end
end

qf = QuickFind.new

%w{5-6 8-9 9-7 0-9 4-9 3-8}.each do |pair|
    pair = pair.split("-")
    qf.union pair[0], pair[1]
end

puts qf.id.join(' ')

wqu = WeightedQuickUnion.new

%w{5-0 2-9 2-8 1-4 0-7 5-3 5-6 4-8 3-4}.each do |pair|
    pair = pair.split("-")
    wqu.union pair[0].to_i, pair[1].to_i
end

puts wqu.id.join(' ')
# puts wqu.sz