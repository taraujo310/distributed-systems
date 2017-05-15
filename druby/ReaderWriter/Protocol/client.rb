require 'drb'

server = DRbObject.new_with_uri('druby://localhost:61676')
number_of_readers = rand(1..100)
number_of_writers = rand(1..100)

server.define_strategy(:favoring_writers)

@threads = []

[number_of_readers, number_of_writers].max.times do |i|
  if i <= number_of_writers
    file = "arq#{rand(1..10)}"
    @threads << Thread.new { server.write(file, rand(0..100)) }
  end

  if i <= number_of_readers
    file = "arq#{rand(1..10)}"
    @threads << Thread.new { puts server.read(file) }
  end
end

@threads.each { |t| t.join }
