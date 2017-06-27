require_relative 'resource.rb'

class NLocker
  def initialize(filepath)
    @resource = initialize_resource(filepath)

    @readCount = 0
    @queueMutex = Mutex.new
    @resourceMutex = Mutex.new
    @readersCounterMutex = Mutex.new
  end

  def access_read
    @queueMutex.synchronize do
      @readersCounterMutex.lock
      @readCount += 1
      @resourceMutex.lock if @readCount == 1
    end
    @readersCounterMutex.unlock

    yield(@resource)

    @readersCounterMutex.synchronize do
      @readCount -= 1
      @resourceMutex.unlock if @readCount == 0
    end
  end

  def access_write
    @queueMutex.synchronize do
      @resourceMutex.lock
    end

    yield(@resource)

    @resourceMutex.unlock
  end

  private
  def initialize_resource(filepath="")
    raise NoNameForFileError, 'There is no file with empty name' unless filepath && !filepath.empty?
    Resource.new(filepath)
  end
end
