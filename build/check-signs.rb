#!/usr/bin/ruby
# prüft welche files signiert wurden

require 'find'

ordner = "src"

SIGNIER_RE = Regexp.new('Signiert durch: (\S+)')

total = 0
done_lines = 0
done_files = []
stats = {
  :Files => Hash.new(0),
  :Lines => Hash.new(0),
}
missing_files = []

Find.find(ordner) do |path|
  Find.prune if path =~ /\.svn/
  
  next if File.stat(path).directory?
  next unless path =~ /\.java$/
  
  content = File.read(path)
  
  lines = content.to_a.size
  total += lines
  
  m = content.match(SIGNIER_RE)
  
  if m
    autor = m[1]
    
    stats[:Files][autor] += 1
    stats[:Lines][autor] += lines
    
    done_lines += lines

    done_files << path
  else
    missing_files << path
  end  
end


puts "Signatur-Report"
puts "==============="

puts
puts "Vorghen:"
puts " - File wählen (idealerweise fremdes)"
puts " - Durchscrollen..."
puts "  .. ist alles javadocisiert?"
puts "  .. hats schwere formale Fehler?"
puts "  .. sonst irgnedwas dringendes?"
puts " - Falls notwendig Todos erstellen"
puts " - Signieren (auch wenn TODOS entstanden sind"
puts "   /* Signiert durch: rschuett */"
puts
puts "Bitte schaut das etwa alle gleich viel machen"

puts 

puts "Letzter Update: #{Time.now}"
puts "Total Zeilen: #{total}"
puts "Geteilt durch fünf: #{total / 5}"
puts "Verbleibend: #{total - done_lines}"

puts

puts "Statistiken"

stats.each do |title, stat|
  puts "#{title}: "
  
  stat.sort{|a, b| b <=> a}.each do |autor, count|
    puts " #{autor}: #{count}"
  end
  puts
end



puts "Unsignierte Files:"

missing_files.sort.each do |m|
  puts " - #{m} #{File.read(m).to_a.size}"
end

puts
puts
puts

puts "Signierte Files:"

done_files.sort.each do |m|
  puts " - #{m} #{File.read(m).to_a.size}"
end





